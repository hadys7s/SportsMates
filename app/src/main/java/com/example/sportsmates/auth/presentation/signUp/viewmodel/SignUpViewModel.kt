package com.example.sportsmates.auth.presentation.signUp.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsmates.auth.data.model.User
import com.example.sportsmates.auth.data.repo.UserRepository1
import com.example.sportsmates.utils.SingleLiveEvent

class SignUpViewModel(private val userRepository: UserRepository1) :
    ViewModel() {
    var signUpSuccess = SingleLiveEvent<Any>()
    var signUpFailed = MutableLiveData<String>()
    var signUpAuthSuccess = SingleLiveEvent<Any>()
    var signUpAuthFailed = MutableLiveData<String>()
    var uploadImageFailed = MutableLiveData<String>()

    init {
        signUpSuccess = userRepository.signUpSuccess
        signUpFailed = userRepository.signUpFailed
        signUpAuthSuccess = userRepository.signUpAuthSuccess
        signUpAuthFailed = userRepository.signUpAuthFailed
        uploadImageFailed = userRepository.uploadImageFailed
    }

    fun onNextEmailButtonCLicked(user: User?, filepath: Uri) {
        userRepository.signUp(user, filepath)
    }


    fun onDoneButtonClicked(user: User?) {
        userRepository.addUserToDataBase(user)
    }

}
