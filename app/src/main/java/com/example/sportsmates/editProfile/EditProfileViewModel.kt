package com.example.sportsmates.editProfile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsmates.ext.getCurrentUserID
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.signUp.data.repo.UserRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    var userData = MutableLiveData<User?>()
    var userImage = MutableLiveData<Uri>()
    var updateInfoFailuer = MutableLiveData<String>()
    var updateInfoSuccess = MutableLiveData<String>()
    var updatePasswordFauiler = MutableLiveData<String?>()
    var updateEmailFauiler = MutableLiveData<String?>()
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

    fun updateUserInfo(user: User) {
        userRepository.updateUserInfo(user)
    }

    fun updateUserAuthentication(
        newEmail: String,
        oldEmail: String,
        newPassword: String,
        oldPassword: String
    ) {
        userRepository.updateUserAuthentication(newEmail, oldEmail, newPassword, oldPassword)

    }
}
