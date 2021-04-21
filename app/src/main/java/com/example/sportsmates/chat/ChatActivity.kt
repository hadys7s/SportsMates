package com.example.sportsmates.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.core.content.ContextCompat
import com.example.sportsmates.R
import com.example.sportsmates.databinding.ActivityChatBinding
import com.example.sportsmates.ext.changeStatusBarColor
import org.koin.android.viewmodel.ext.android.viewModel

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatBinding.inflate(layoutInflater)
        val view =binding.root
        setContentView(view)
        changeStatusBarColor(R.color.main_green)
        attachClickListeners()

    }
    private fun attachClickListeners(){
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}