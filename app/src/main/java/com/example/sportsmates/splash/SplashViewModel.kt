package com.example.sportsmates.splash

import androidx.lifecycle.ViewModel
import com.example.sportsmates.signUp.data.Repo.UserRepository
import com.example.sportsmates.utils.SingleLiveEvent

class SplashViewModel(val userRepository: UserRepository) : ViewModel() {
    val authenticationNavigationEvent = SingleLiveEvent<Any>()
    val homeNavigationEvent = SingleLiveEvent<Any>()

    fun userAuthorized() {
        if (userRepository.checkCurrentUserAuthorization()) {
            homeNavigationEvent.call()
        } else {
            authenticationNavigationEvent.call()
        }
    }

}