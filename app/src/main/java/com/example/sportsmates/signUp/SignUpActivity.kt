package com.example.sportsmates.signUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.sportsmates.R
import com.example.sportsmates.SignUp.SignInFragment
import com.example.sportsmates.coach.CoachDetailsFragment
import com.example.sportsmates.ext.replaceFragment

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        replaceFragment(CoachDetailsFragment.newInstance(), containerViewId = R.id.container)
    }
}