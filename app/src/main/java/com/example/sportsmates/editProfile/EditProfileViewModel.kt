package com.example.sportsmates.editProfile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.signUp.data.repo.UserRepository

class EditProfileViewModel(private val userRepository: UserRepository):ViewModel() {
    var userData = MutableLiveData<User?>()
    var userImage = MutableLiveData<Uri>()

    init {
        userData = userRepository.userData
        userImage = userRepository.retriveImage
    }

    fun fetchUserData() {
        userRepository.fetchUserData()
    }
    fun getUserImage() {
        userRepository.retrievePhoto()
    }
}