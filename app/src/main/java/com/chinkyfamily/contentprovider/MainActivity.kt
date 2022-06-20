package com.chinkyfamily.contentprovider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chinkyfamily.contentprovider.databinding.ActivityMainBinding

/**
 * MainActivity of the Application.
 * */
class MainActivity : AppCompatActivity()
{
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding

    /**
     * onCreate callback method of the Activity.
     * */
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    /**
     * onStart callback method of the Activity.
     * */
    override fun onStart()
    {
        super.onStart()
        binding?.btnContactListCoroutine?.setOnClickListener {
            Intent(this , ContactListCoroutine::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)
            }
        }
        binding?.btnContactWorkManager?.setOnClickListener {
            Intent(this , ContactListWorkManager::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)
            }
        }
    }
}