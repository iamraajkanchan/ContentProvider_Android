package com.chinkyfamily.contentprovider.workers

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.chinkyfamily.contentprovider.ContactListWorkManager
import com.chinkyfamily.contentprovider.interfaces.ContactListInterface

/**
 * ContactListWorker
 * */
class ContactListWorker(private val wContext : Context , private val worker : WorkerParameters) :
    Worker(wContext , worker)
{
    private var dataList : ArrayList<String>? = null
    private lateinit var contactListInterface : ContactListInterface

    /** doWork callback method of Worker. */
    override fun doWork() : Result
    {
        return try
        {
            Result.success()
        } catch (e : Exception)
        {
            Result.failure()
        }
    }

    fun registerCallback(contactListInterface : ContactListInterface)
    {
        this.contactListInterface = contactListInterface
        sendResultFromWorker()
    }

    /**
     * sendResultWorker is used to share contactList to ContactListWorkManager activity.
     * */
    private fun sendResultFromWorker()
    {
        this.contactListInterface.shareContactList(dataList ?: return)
    }

    /**
     * ContactListWorkerInstantiate class is used to create an instance of ContactListWorker.
     * */
    inner class ContactListWorkerInstantiate()
    {
        fun getInstance() : ContactListWorker = ContactListWorker(wContext , worker)
    }
}