package com.chinkyfamily.contentprovider.workers

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.Data
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
            val outPutData = Data.Builder().putString("Shared Key" , "Hello WorkManager").build()
            Result.success(outPutData)
        } catch (e : Exception)
        {
            Result.failure()
        }
    }

    /**
     * registerCallback couldn't access this method out of ContactListWorker class.
     * */
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
        /**
         * getInstance method is used to create an instance of ContactListWorker class.
         * At the moment this is not working.
         * */
        fun getInstance() : ContactListWorker = ContactListWorker(wContext , worker)
    }
}