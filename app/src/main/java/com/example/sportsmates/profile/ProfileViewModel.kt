package com.example.sportsmates.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.auth.data.model.User
import com.example.sportsmates.auth.domain.datainterfaces.UserRepository
import com.example.sportsmates.networking.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private var _userData = MutableStateFlow<Resource<User>>(Resource.Loading)
    val userData get() = _userData.asStateFlow()


    fun fetchUserData() = viewModelScope.launch {
        userRepository.getUserInfo()
            .catch {
                _userData.emit(Resource.Error(it))
            }
            .collect { user ->
                user?.let { _userData.emit(Resource.Success(it)) }
            }
    }

    fun logout() = viewModelScope.launch {
        userRepository.logout()
    }

}