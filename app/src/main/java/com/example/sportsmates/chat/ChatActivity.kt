package com.example.sportsmates.chat

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsmates.R
import com.example.sportsmates.chat.adapters.UsersAdapter
import com.example.sportsmates.chat.model.MessageModel
import com.example.sportsmates.databinding.ActivityChatBinding
import com.example.sportsmates.ext.changeStatusBarColor
import com.example.sportsmates.ext.stopShimmer
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.signUp.data.model.toMessageModel
import org.koin.android.viewmodel.ext.android.viewModel

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val viewModel: ChatViewModel by viewModel()
    private lateinit var userChatListAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        changeStatusBarColor(R.color.main_green)
        attachClickListeners()
        setupList()
        attachObservers()
    }

    private fun attachObservers(){
        viewModel.getUserListOfChat()
        viewModel.listOfChat.observe(this, Observer { listOfChat ->
            setUserChatList(listOfChat)
            stopShimmer(binding.shimmerViewContainer)
        })
    }

    private fun setUserChatList(listOfChat: List<MessageModel>?) {
        userChatListAdapter = UsersAdapter(listOfChat?.reversed(), this)
        binding.chatList.adapter = userChatListAdapter
        userChatListAdapter.onItemClick = { messageModel, imageView ->
            openActivityWithTransitionAnimation(messageModel,imageView)
        }
    }

    private fun openActivityWithTransitionAnimation(
        messageModel: MessageModel,
        targetImage: ImageView
    ) {
        val option =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, targetImage, "img")
                .toBundle()
        MessagesActivity.start(this, messageModel, option!!)

    }


    private fun setupList() {
        binding.chatList.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

    }

    private fun attachClickListeners() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}