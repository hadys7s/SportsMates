package com.example.sportsmates.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.sportsmates.R
import org.koin.android.viewmodel.ext.android.viewModel

class SignInActivity : AppCompatActivity() {
    val viewModel: SignInViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        attachEventObservers()
        attachCLickListeners()

    }

    private fun attachEventObservers() {
        viewModel.loginSuccess.observe(this, Observer { user ->
            //  redirect home

        })
        viewModel.loginFailed.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        })
    }

    private fun attachCLickListeners() {
        val button: Button = findViewById(R.id.signIn)
        button.setOnClickListener {
            viewModel.login("hady815@gmail.com", "Hadys7s@")
        }

    }
}