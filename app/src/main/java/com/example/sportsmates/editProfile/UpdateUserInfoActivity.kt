package com.example.sportsmates.editProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sportsmates.R
import com.example.sportsmates.databinding.ActivityUpdateUserInfoBinding
import com.example.sportsmates.ext.changeStatusBarColor
import org.koin.android.viewmodel.ext.android.viewModel

class UpdateUserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateUserInfoBinding
    private val viewModel:EditProfileViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUpdateUserInfoBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        changeStatusBarColor(R.color.main_green)
        attachClickListener()
        getEditField()
    }
    private fun getEditField(){
        val intent=intent
       val text= intent.getStringExtra("Edit")
        val hint=intent.getStringExtra("Hint")
        binding.editField.setText(text)
        binding.editBox.hint = hint
    }
    private fun attachClickListener(){
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.updateBtn.setOnClickListener {

        }
    }
}