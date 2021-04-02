package com.example.sportsmates.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import com.example.sportsmates.R
import com.example.sportsmates.ext.openTopActivity
import com.example.sportsmates.ext.setFullScreenWithTransparentStatusBar
import com.example.sportsmates.home.MainActivity
import com.example.sportsmates.signUp.SignUpActivity
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        attachEventObservers()
        setFullScreenWithTransparentStatusBar()
        Timer().schedule(DELAY_FINISH) {
            // do something after 1 second
            viewModel.userAuthorized()
        }


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

    companion object {
        private const val DELAY_FINISH: Long = 4000

    }
}