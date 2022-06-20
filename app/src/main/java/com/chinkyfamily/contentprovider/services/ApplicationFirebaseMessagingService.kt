package com.chinkyfamily.contentprovider.services

import com.google.firebase.messaging.FirebaseMessagingService

/**
 * ApplicationFirebaseMessagingService used for the application.
 * */
class ApplicationFirebaseMessagingService : FirebaseMessagingService()
{

    /**
     * onNewToken callback method of FirebaseMessagingService.
     * */
    override fun onNewToken(token : String)
    {
        println("ApplicationFirebaseMessagingService :: onNewToken :: token :  $token")
    }
}