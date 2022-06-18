package com.chinkyfamily.contentprovider

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.chinkyfamily.contentprovider.databinding.ActivityContactListBinding

class ContactList : AppCompatActivity()
{
    companion object
    {
        const val READ_CONTACTS_REQUEST_CODE : Int = 1001
    }

    private var _binding : ActivityContactListBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        _binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        fetchContacts()
    }

    private fun fetchContacts()
    {
        if (ContextCompat.checkSelfPermission(this ,
                android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(this ,
                arrayOf(android.Manifest.permission.READ_CONTACTS) ,
                READ_CONTACTS_REQUEST_CODE)
        }
        val resolver = contentResolver
    }
}