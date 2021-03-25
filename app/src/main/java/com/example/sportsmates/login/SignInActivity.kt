package com.example.sportsmates.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.sportsmates.R
import com.example.sportsmates.databinding.ActivityMainBinding
import com.example.sportsmates.databinding.ActivitySignInBinding
import org.koin.android.viewmodel.ext.android.viewModel

class SignInActivity : AppCompatActivity() {
    val viewModel: SignInViewModel by viewModel()
    private lateinit var binding: ActivitySignInBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
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

       /* binding.button.setOnClickListener {
            viewModel.login("hady815@gmail.com", "Hadys7s@")
        }*/

    }
}