package com.example.sportsmates.discover

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.sportsmates.databinding.ActivityContactsDetailBinding
import com.example.sportsmates.ext.setFullScreenWithTransparentStatusBar
import com.example.sportsmates.signUp.data.model.User
import org.koin.android.viewmodel.ext.android.viewModel

class ContactsDetails : AppCompatActivity() {
    private lateinit var binding: ActivityContactsDetailBinding
    private val viewModel: ContactsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setFullScreenWithTransparentStatusBar()
        attachCLickListeners()
        fetchUserData()
    }


    private fun fetchUserData() {
        val user: User? = intent.getParcelableExtra(USER_ITEM)
        bindData(user)
    }

    private fun bindData(user: User?) {
        binding.userName.text = user?.name
        binding.useraAddress.text = user?.city
        binding.userAge.text = user?.age + " " + "years"
        binding.sports1.text = user?.sportsList?.get(0)
        if (user?.sportsList?.size!! > 1) {
            binding.sports2.text = user.sportsList?.get(1)
            binding.sports2.isVisible = true

            if (user.sportsList?.size!! > 2) {
                binding.sports3.text = user.sportsList?.get(2)
                binding.sports3.isVisible = true

            }
        }
        Glide.with(this)
            .load(user.userImage)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.userDetailImage)
    }

    private fun attachCLickListeners() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        private const val USER_ITEM = "userItem"
        fun start(activity: FragmentActivity?, userItem: User, options: Bundle) {
            val intent = Intent(activity, ContactsDetails::class.java)
            intent.putExtra(USER_ITEM, userItem)
            activity?.startActivity(intent, options)
        }
    }

}
