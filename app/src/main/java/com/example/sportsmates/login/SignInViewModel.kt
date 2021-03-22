package com.example.sportsmates.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsmates.SignUp.data.Repo.UserRepository
import com.example.sportsmates.SignUp.data.model.User
import com.example.sportsmates.Utils.SingleLiveEvent

class SignInViewModel(private val userRepository: UserRepository) :
    ViewModel() {

    var loginFailed = MutableLiveData<String>()
    var loginSuccess = SingleLiveEvent<Any>()

    init {
        loginFailed = userRepository.loginFailed
        loginSuccess = userRepository.loginSuccess
    }


    fun login(email: String, password: String) {
        userRepository.login(email, password)
    }

}