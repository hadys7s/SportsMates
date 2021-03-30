package com.example.sportsmates.signUp.data.Repo

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.utils.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class UserRepository(
    private val userAuth: FirebaseAuth
) {

    var signUpAuthSuccess = SingleLiveEvent<Any>()
    var signUpAuthFailed = MutableLiveData<String>()
    var uploadImageFailed = MutableLiveData<String>()
    var retriveImage=MutableLiveData<Uri>()
    var signUpSuccess = SingleLiveEvent<Any>()
    var signUpFailed = MutableLiveData<String>()
    var loginFailed = MutableLiveData<String>()
    var loginSuccess = SingleLiveEvent<Any>()
    var userData = MutableLiveData<User?>()


    fun login(email: String, password: String) {
        userAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginSuccess.postValue(userAuth.currentUser.uid)

                } else {
                    loginFailed.postValue(task.exception?.message)
                    Log.w(TAG, "loginFailed:failure", task.exception)
                }

            }

    }

    fun logout() {
        userAuth.signOut()
    }

    fun signUp(user: User?, filepath: Uri) {
        // [START create_user_with_email]
        userAuth.createUserWithEmailAndPassword(user?.email, user?.password)
            .addOnSuccessListener {
                uploadPhoto(filepath)
            }.addOnFailureListener {
                Log.w(TAG, "createUserWithEmail:failure", it)
                signUpAuthFailed.postValue(it.message.toString())
            }
    }


    fun addUserToDataBase(user: User?) {
        FirebaseDatabase.getInstance().getReference("Users")
            .child(userAuth.currentUser.uid)
            .setValue(user).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "userAddedToDataBase:Success")
                    signUpSuccess.call()


                } else {
                    Log.d(TAG, "userAddedToDataBase:Failed")
                    signUpFailed.postValue(task.exception?.message)
                }
            }
    }

    fun fetchUserData() {
        FirebaseDatabase.getInstance().getReference("Users")
            .child(Firebase.auth.currentUser.uid)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "getUser:Success")
                    userData.postValue(task.result?.getValue(User::class.java))

                } else {
                    Log.d(TAG, "getUser:Failed", task.exception)

                }
            }
    }

   private fun uploadPhoto(filepath: Uri) {

        val storageReference =
            FirebaseStorage.getInstance().reference.child("images/" + userAuth.currentUser.uid)
        storageReference.putFile(filepath)

            .addOnSuccessListener {
                Log.d(TAG, "ImageUpload:Success")
                signUpAuthSuccess.call()
            }
            .addOnFailureListener {
                uploadImageFailed.postValue(it.message.toString())
            }

    }
      fun retrivePhoto(){
        val storageReference =
            FirebaseStorage.getInstance().reference.child("images/" + userAuth.currentUser.uid)
               storageReference .downloadUrl.addOnSuccessListener {imageUri->
                    Log.d(TAG, "ImageUpload:Success")
                   retriveImage.postValue(imageUri)
                }
                .addOnFailureListener {
                    Log.d(TAG, it.toString())
                }

    }


    fun checkCurrentUserAuthorization(): Boolean {
        // [START check_current_user]
        val user = userAuth.currentUser
        return user != null
        // [END check_current_user]
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

}