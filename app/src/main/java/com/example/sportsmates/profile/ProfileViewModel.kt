package com.example.sportsmates.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsmates.auth.data.repo.UserRepository1
import com.example.sportsmates.auth.data.model.User

class ProfileViewModel(private val userRepository: UserRepository1) : ViewModel() {

    var userData = MutableLiveData<User>()
    var userImage = MutableLiveData<Uri>()


    init {
        userData = userRepository.userData
        userImage = userRepository.retriveImage
        fetchUserData()
        getUserImage()
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