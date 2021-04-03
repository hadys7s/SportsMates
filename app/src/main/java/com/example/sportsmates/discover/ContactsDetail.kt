package com.example.sportsmates.discover

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sportsmates.R
import com.example.sportsmates.databinding.ActivityCoashDetailsBinding
import com.example.sportsmates.databinding.ActivityContactsDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ContactsDetail : AppCompatActivity() {
    private lateinit var binding: ActivityContactsDetailBinding
    private val viewModel: ContactsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}