package com.chinkyfamily.contentprovider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chinkyfamily.contentprovider.databinding.ActivityMainBinding

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
}