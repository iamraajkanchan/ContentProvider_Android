package com.chinkyfamily.contentprovider

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.chinkyfamily.contentprovider.databinding.ActivityContactListBinding
import kotlinx.coroutines.*

/**
 * ContactList
 * */
class ContactList : AppCompatActivity()
{
    companion object
    {
        /**
         * Request code for Reading contacts from My Contacts.
         * */
        const val READ_CONTACTS_REQUEST_CODE : Int = 1001
    }

    private var _binding : ActivityContactListBinding? = null
    private val binding get() = _binding

    /**
     * onCreate callback method of the Activity
     * */
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        _binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        MainScope().launch {
            val listData = withContext(Dispatchers.Default) { fetchContacts() }
            val contactsAdapter =
                ArrayAdapter(this@ContactList , android.R.layout.simple_list_item_1 , listData)
            binding?.listContacts?.adapter = contactsAdapter
        }
    }

    @SuppressLint("Range")
    private suspend fun fetchContacts() : ArrayList<String>
    {
        if (ContextCompat.checkSelfPermission(this ,
                android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(this ,
                arrayOf(android.Manifest.permission.READ_CONTACTS) ,
                READ_CONTACTS_REQUEST_CODE)
        }
        val resolver : ContentResolver = contentResolver
        val phoneUri : Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val emailUri : Uri = ContactsContract.CommonDataKinds.Email.CONTENT_URI
        val projections = null
        val selections = null
        val selectionArgs = null
        val order = null
        val dataFromContacts = ArrayList<String>()

        val cursor = resolver.query(emailUri , projections , selections , selectionArgs , order)
        withContext(Dispatchers.IO) {
            if ((cursor?.count ?: return@withContext) > 0)
            {
                while (cursor.moveToNext())
                {
                    val contactName =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME_PRIMARY))
                    val contactNumber =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS))
                    val fullContactDetails = "$contactName\n$contactNumber "
                    dataFromContacts?.add(fullContactDetails)
                }
            }
        }
        return dataFromContacts
    }

    /**
     * onRequestPermissionResult
     * */
    override fun onRequestPermissionsResult(
        requestCode : Int ,
        permissions : Array<out String> ,
        grantResults : IntArray ,
    )
    {
        super.onRequestPermissionsResult(requestCode , permissions , grantResults)
        if (requestCode == READ_CONTACTS_REQUEST_CODE)
        {
            CoroutineScope(Dispatchers.Main).launch {
                fetchContacts()
            }
        }
    }
}