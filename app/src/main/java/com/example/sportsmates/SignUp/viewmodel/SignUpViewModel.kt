package com.example.sportsmates.SignUp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsmates.SignUp.data.Repo.UserRepository
import com.example.sportsmates.SignUp.data.model.User
import com.example.sportsmates.Utils.SingleLiveEvent
import com.google.firebase.auth.FirebaseUser

class SignUpViewModel(private val userRepository: UserRepository) :
    ViewModel() {
    var signUpSuccess = SingleLiveEvent<Any>()
    var signUpFailed = MutableLiveData<String>()

    init {
        signUpSuccess = userRepository.signUpSuccess
        signUpFailed = userRepository.signUpFailed
    }

    fun onRegisterButtonCLicked(user: User, password: String) {
        userRepository.signUp(user, password)
    }


}

