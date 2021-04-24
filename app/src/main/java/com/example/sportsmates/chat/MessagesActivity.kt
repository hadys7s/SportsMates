package com.example.sportsmates.chat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.sportsmates.R
import com.example.sportsmates.chat.adapters.ChatAdapter
import com.example.sportsmates.chat.model.Chat
import com.example.sportsmates.databinding.ActivityMessagesBinding
import com.example.sportsmates.ext.changeStatusBarColor
import com.example.sportsmates.signUp.data.model.User
import org.koin.android.viewmodel.ext.android.viewModel

class MessagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessagesBinding
    private val viewModel: ChatViewModel by viewModel()
    private lateinit var adapter: ChatAdapter
    private var userId: String? = null
    private var userName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        changeStatusBarColor(R.color.main_green)
        setupList()
        attachClickListeners()
        fetchUserData()
        attachObservers()
    }

    private fun attachClickListeners() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.sendBtn.setOnClickListener {
            sendMessage()
            binding.edMessage.text.clear()
            attachObservers()
        }
    }

    private fun attachObservers() {
        viewModel.readMessage(userId)
        viewModel.retriveChatSuceess.observe(this, Observer {
            setMessage(it)
        })
    }

    private fun setMessage(listOfChat: List<Chat>?) {
        adapter = ChatAdapter(listOfChat, this)
        binding.coachList.adapter = adapter
    }

    private fun setupList() {
        binding.coachList.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

    }

    private fun fetchUserData() {
        val user: User? = intent.getParcelableExtra(USER_ITEM)
        userId = user?.id
        bindData(user)
    }

    private fun bindData(user: User?) {
        binding.userName.text = user!!.name
        Glide.with(this)
            .load(user.userImage)
            .circleCrop()
            .into(binding.userImage)
    }

    private fun sendMessage() {
        val message = binding.edMessage.text.toString()
        if (message.isNotEmpty()) {
            viewModel.sendMessage(userId!!, message)
        } else {
            Toast.makeText(this, "field is empty", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val USER_ITEM = "userItem"
        fun start(activity: FragmentActivity?, userItem: User, options: Bundle) {
            val intent = Intent(activity, MessagesActivity::class.java)
            intent.putExtra(USER_ITEM, userItem)
            activity?.startActivity(intent, options)
        }
    }
}