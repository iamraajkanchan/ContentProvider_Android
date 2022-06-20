package com.chinkyfamily.contentprovider

import android.R
import android.annotation.SuppressLint
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import androidx.loader.content.CursorLoader
import com.chinkyfamily.contentprovider.databinding.ActivityContactListLoaderBinding

/**
 * ContactListLoader created with LoaderManager.LoaderCallbacks
 * */
class ContactListLoader : AppCompatActivity() , android.app.LoaderManager.LoaderCallbacks<Cursor?>
{
    private var _binding : ActivityContactListLoaderBinding? = null
    private val binding get() = _binding
    private var listData : ArrayList<String>? = null
    private var isLoaded : Boolean = false

    /**
     * onCreate callback method of the Activity.
     * */
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        _binding = ActivityContactListLoaderBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        listData = ArrayList()
        if (isLoaded)
        {
            loaderManager.initLoader(1 , null , this)
        } else
        {
            loaderManager.restartLoader(1 , null , this)
        }
    }

    override fun onCreateLoader(i : Int , bundle : Bundle?) : Loader<Cursor?>?
    {
        if (i == 1)
        {
            val phoneUri : Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

            // return CursorLoader(this !! , phoneUri , null , null , null , null)
        }
        return null
    }

    /**
     * onLoadFinished callback method of LoadManager.LoaderCallbacks<Cursor>
     * */
    @Deprecated("Deprecated in Java")
    @SuppressLint("Range")
    override fun onLoadFinished(loader : android.content.Loader<Cursor?>? , cursor : Cursor?)
    {
        if (cursor != null && cursor.count > 0)
        {
            while (cursor.moveToNext())
            {
                val contactName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val contactNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val fullContactDetails = "$contactName\n$contactNumber "
                listData?.add(fullContactDetails)
            }
        }
        val contactsAdapter = ArrayAdapter(this , R.layout.simple_list_item_1 , listData ?: return)
        binding?.listContacts?.adapter = contactsAdapter
        isLoaded = true
    }

    /**
     * onLoadFinished callback method of LoadManager.LoaderCallbacks<Cursor>
     * */
    @Deprecated("Deprecated in Java")
    override fun onLoaderReset(loader : android.content.Loader<Cursor?>?)
    {

    }
}