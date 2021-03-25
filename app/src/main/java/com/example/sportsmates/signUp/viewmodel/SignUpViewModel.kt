package com.example.sportsmates.signUp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsmates.signUp.data.Repo.UserRepository
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.utils.SingleLiveEvent

class SignUpViewModel(private val userRepository: UserRepository) :
    ViewModel() {
    var signUpSuccess = SingleLiveEvent<Any>()
    var signUpFailed = MutableLiveData<String>()

    init {
        signUpSuccess = userRepository.signUpSuccess
        signUpFailed = userRepository.signUpFailed
    }

    fun onRegisterButtonCLicked(user: User?) {
        userRepository.signUp(user)
    }


}

