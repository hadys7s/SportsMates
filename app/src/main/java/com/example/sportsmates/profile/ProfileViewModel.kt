package com.example.sportsmates.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsmates.signUp.data.Repo.UserRepository
import com.example.sportsmates.signUp.data.model.User

class ProfileViewModel(val userRepository: UserRepository) : ViewModel() {

    var userData = MutableLiveData<User>()

    fun fetchUserData() {
        userData.postValue(userRepository.fetchUserData())
    }
}