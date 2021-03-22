package com.example.sportsmates.SignUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.sportsmates.R
import com.example.sportsmates.SignUp.data.model.User
import com.example.sportsmates.SignUp.viewmodel.SignUpViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class SignUpActivity : AppCompatActivity() {
    private val viewModel: SignUpViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        //auth = Firebase.auth
        val sportsList = listOf("chess", "Hockey", "BasketBall")
        val user = User(
            "Zain",
            "hadyhessen.hh@gmail.com",
            "01061525442",
            "21",
            "male",
            "Mansoura",
            sportsList
        )
        val register: Button = findViewById(R.id.registerBtn)
        register.setOnClickListener {
            viewModel.onRegisterButtonCLicked(user, "Hady123456")
        }
        attachEventObservers()


    }

    private fun attachEventObservers() {
        viewModel.signUpSuccess.observe(this, Observer {
            // redirect login
        })

        viewModel.signUpFailed.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()

        })

    }
}