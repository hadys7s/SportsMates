package com.example.sportsmates.signUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.sportsmates.R
import com.example.sportsmates.SignUp.SignInFragment
import com.example.sportsmates.coach.CoachFragment
import com.example.sportsmates.ext.replaceFragment
import com.example.sportsmates.ext.setFullScreenWithTransparentStatusBar
import com.example.sportsmates.news.presentation.fragment.NewsFragment

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setFullScreenWithTransparentStatusBar()
        replaceFragment(NewsFragment.newInstance(), containerViewId = R.id.container)
    }
}