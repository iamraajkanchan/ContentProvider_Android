package com.chinkyfamily.contentprovider

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
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
        const val READ_CONTACTS_REQUEST_CODE : Int = 1001
    }

    private var _binding : ActivityContactListBinding? = null
    private val binding get() = _binding
    private var listData : ArrayList<String>? = null

    /**
     * onCreate callback method of the Activity
     * */
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        _binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        listData = ArrayList()
        MainScope().launch {
            val contacts = async { fetchContacts() }
            listData = contacts.await()
            println("ContactList :: onCreate :: MainScope :: listData ${listData.toString()}")
        }
        val contactsAdapter : ArrayAdapter<String> =
            ArrayAdapter(this , android.R.layout.simple_list_item_1 , listData ?: return)
        binding?.listContacts?.adapter = contactsAdapter
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
        val uri : Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projections = null
        val selections = null
        val selectionArgs = null
        val order = null
        val dataFromContacts = ArrayList<String>()

        val cursor = resolver.query(uri , projections , selections , selectionArgs , order)
        withContext(Dispatchers.IO) {
            if ((cursor?.count ?: return@withContext) > 0)
            {
                while (cursor.moveToNext())
                {
                    val contactName =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val contactNumber =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
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