package com.example.sportsmates.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.sportsmates.R
import com.example.sportsmates.databinding.ActivityChatBinding
import com.example.sportsmates.ext.changeStatusBarColor
import org.koin.android.viewmodel.ext.android.viewModel

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val viewModel: ChatViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        changeStatusBarColor(R.color.main_green)
        attachClickListeners()
        viewModel.retriveChatErorr.observe(this, Observer {

        })

    }
    private fun attachClickListeners(){
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}