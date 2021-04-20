package com.example.sportsmates.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.example.sportsmates.R
import com.example.sportsmates.databinding.ActivityMessagesBinding
import com.example.sportsmates.discover.ContactsDetails
import com.example.sportsmates.ext.changeStatusBarColor
import com.example.sportsmates.signUp.data.model.User

class MessagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessagesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMessagesBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        changeStatusBarColor(R.color.main_green)
        attachClickListeners()
        fetchUserData()
    }
    private fun attachClickListeners(){
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
    private fun fetchUserData() {
        val user: User? = intent.getParcelableExtra(USER_ITEM)
        bindData(user)
    }
    private fun bindData(user:User?){
        binding.userName.text=user!!.name
        Glide.with(this)
            .load(user.userImage)
            .circleCrop()
            .into(binding.userImage)
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