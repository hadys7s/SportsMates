package com.example.sportsmates.signUp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsmates.R
import com.example.sportsmates.SignUp.SignInFragment
import com.example.sportsmates.ext.replaceFragment
import com.example.sportsmates.ext.setFullScreenWithTransparentStatusBar


class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setFullScreenWithTransparentStatusBar()
        replaceFragment(SignInFragment.newInstance(), containerViewId = R.id.container)
    }

}