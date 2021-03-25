package com.example.sportsmates.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.sportsmates.R
import com.example.sportsmates.ext.openTopActivity
import com.example.sportsmates.home.MainActivity
import com.example.sportsmates.signUp.SignUpActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        attachEventObservers()
        viewModel.userAuthorized()
    }

    private fun attachEventObservers() {
        viewModel.authenticationNavigationEvent.observe(this, Observer {
            //  redirect signIn
            openTopActivity(this, SignUpActivity())


        })
        viewModel.homeNavigationEvent.observe(this, Observer {
            // redirect home
            openTopActivity(this, MainActivity())

        })
    }
}