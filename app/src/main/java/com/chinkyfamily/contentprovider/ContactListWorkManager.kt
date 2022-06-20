package com.chinkyfamily.contentprovider

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.chinkyfamily.contentprovider.R.*
import com.chinkyfamily.contentprovider.databinding.ActivityContactListWorkManagerBinding
import com.chinkyfamily.contentprovider.workers.ContactListWorker

/**
 * ContactListWorkManager
 * */
class ContactListWorkManager : AppCompatActivity()
{
    private var _binding : ActivityContactListWorkManagerBinding? = null
    private val binding get() = _binding

    /**
     * onCreate callback method of the Activity.
     * */
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        _binding = ActivityContactListWorkManagerBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        if (ContextCompat.checkSelfPermission(this ,
                android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(this ,
                arrayOf(android.Manifest.permission.READ_CONTACTS) ,
                ContactListCoroutine.READ_CONTACTS_REQUEST_CODE)
        }
        fetchContactList()
        val contactList = ArrayList<String>()
        val adapter = ArrayAdapter(this , android.R.layout.simple_list_item_1 , contactList)
        binding?.listContact?.adapter = adapter
    }

    private fun fetchContactList()
    {
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(ContactListWorker::class.java).build()
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)
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
        if (requestCode == ContactListCoroutine.READ_CONTACTS_REQUEST_CODE)
        {
            fetchContactList()
        }
    }
}