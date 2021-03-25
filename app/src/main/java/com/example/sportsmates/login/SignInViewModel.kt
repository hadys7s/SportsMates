package com.example.sportsmates.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsmates.signUp.data.Repo.UserRepository
import com.example.sportsmates.utils.SingleLiveEvent

class SignInViewModel(private val userRepository: UserRepository) :
    ViewModel() {

    var loginFailed = MutableLiveData<String>()
    var loginSuccess = MutableLiveData<String>()

    init {
        loginFailed = userRepository.loginFailed
        loginSuccess = userRepository.loginSuccess
    }


    fun login(email: String, password: String) {
        userRepository.login(email, password)
    }

}