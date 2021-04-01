package com.example.sportsmates.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsmates.signUp.data.Repo.UserRepository
import com.example.sportsmates.signUp.data.model.User

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    var userData = MutableLiveData<User?>()
    var userImage = MutableLiveData<Uri>()

    init {
        userData = userRepository.userData
        userImage = userRepository.retriveImage
    }

    fun fetchUserData() {
        userRepository.fetchUserData()
    }

    fun logout() {
        userRepository.logout()
    }

    fun getUserImage() {
        userRepository.retrievePhoto()
    }
}