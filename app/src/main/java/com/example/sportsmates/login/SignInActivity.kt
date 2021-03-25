package com.example.sportsmates.login
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.sportsmates.SignUp.SignUpActivity
import com.example.sportsmates.databinding.ActivitySignInBinding
import org.koin.android.viewmodel.ext.android.viewModel


class SignInActivity : AppCompatActivity() {
    private val viewModel: SignInViewModel by viewModel()
    private lateinit var binding:ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        signUp()
    }
    private fun attachEventObservers() {
        viewModel.loginSuccess.observe(this, Observer { user ->
            //  redirect home

        })
        viewModel.loginFailed.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        })
    }
    private fun signUp (){
        binding.tvSelectableSignup.setOnClickListener {
             val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}