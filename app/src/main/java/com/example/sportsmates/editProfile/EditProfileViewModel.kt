package com.example.sportsmates.editProfile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsmates.auth.data.model.User
import com.example.sportsmates.auth.data.repo.UserRepository

class EditProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    var userData = MutableLiveData<User>()
    var userImage = MutableLiveData<Uri>()
    var updateInfoFailuer = MutableLiveData<String>()
    var updateInfoSuccess = MutableLiveData<String>()
    var uploadImageFailed = MutableLiveData<String>()
    var uploadImageSuccess = MutableLiveData<String>()

    init {
        userData = userRepository.userData
        userImage = userRepository.retriveImage
        uploadImageFailed = userRepository.uploadImageFailed
        uploadImageSuccess = userRepository.uploadImageSucess
        updateInfoSuccess = userRepository.updateInfoSuccess
        updateInfoFailuer = userRepository.updateInfoFailuer
    }

    fun fetchUserData() {
        userRepository.fetchUserData()
    }

    fun getUserImage() {
        userRepository.retrievePhoto()
    }

    fun uploadProfileImage(filePath: Uri) {
        userRepository.uploadPhoto(filePath)
    }

    fun updateUserName(name:String){
        userRepository.updateUserName(name)
    }
    fun updateUserEmail(newEmail:String,oldPassword: String){
        userRepository.updateUserAuthenticationEmail(newEmail, oldPassword)
    }
    fun updateUserCity(city:String){
        userRepository.updateUserCity(city)
    }
    fun updateUserPassword(newPassword:String,oldPassword: String){
        userRepository.updateUserAuthenticationPassword(newPassword, oldPassword)
    }
    fun updateUserSportsList(sports:List<String>){
        userRepository.updateUserSportsList(sports)
    }
    fun updateUserBio(bio:String){
        userRepository.updateUserBio(bio)
    }

}
