package com.example.sportsmates.editProfile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.auth.data.model.User
import com.example.sportsmates.auth.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class EditProfileViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    var userData = MutableLiveData<User>()
    var userImage = MutableLiveData<Uri>()
    var updateInfoFailuer = MutableLiveData<String>()
    var updateInfoSuccess = MutableLiveData<String>()
    var uploadImageFailed = MutableLiveData<String>()
    var uploadImageSuccess = MutableLiveData<String>()

    fun fetchUserData() {
        viewModelScope.launch {
            userData.value = userUseCase.getUserInfo().last()
        }
    }

    fun uploadProfileImage(filePath: Uri) {
        viewModelScope.launch {
            userUseCase.uploadUserImage(filePath)
        }
    }

    fun updateUserName(name: String) {
        viewModelScope.launch {
            userUseCase.updateUserName(name)
        }
    }

    fun updateUserEmail(newEmail: String, oldPassword: String) {
        viewModelScope.launch {
            userUseCase.updateUserEmail(newEmail, oldPassword)
        }
    }

    fun updateUserCity(city: String) {
        viewModelScope.launch {
            userUseCase.updateUserCity(city)
        }
    }

    fun updateUserPassword(newPassword: String, oldPassword: String) {
        viewModelScope.launch {
            userUseCase.updateUserPassword(newPassword, oldPassword)
        }
    }

    fun updateUserSportsList(sports: List<String>) {
        viewModelScope.launch {
            userUseCase.updateUserSportsList(sports)
        }
    }

    fun updateUserBio(bio: String) {
        viewModelScope.launch {
            userUseCase.updateUserBio(bio)

        }
    }

}
