package com.example.sportsmates.signUp.data.repo

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sportsmates.UserPreferences
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.utils.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class UserRepository(
    private val userAuth: FirebaseAuth,
    private val userpref: UserPreferences
) {

    var signUpAuthSuccess = SingleLiveEvent<Any>()
    var signUpAuthFailed = MutableLiveData<String>()
    var uploadImageFailed = MutableLiveData<String>()
    var retriveImage = MutableLiveData<Uri>()
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
        userpref.clear()
        userAuth.signOut()
    }

    fun deleteUser() {
        Firebase.auth.currentUser.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                }

            }
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
                    val user = task.result?.getValue(User::class.java)
                    userData.postValue(user!!)
                    userpref.name = user.name
                } else {
                    Log.d(TAG, "getUser:Failed", task.exception)

                }
            }
    }

    private fun uploadPhoto(filepath: Uri) {
        val storageReference =
            FirebaseStorage.getInstance().reference.child("userImages/" + userAuth.currentUser.uid)

        storageReference.putFile(filepath)
            .addOnSuccessListener { task ->
                Log.d(TAG, "ImageUpload:Success")
                signUpAuthSuccess.call()
            }
            .addOnFailureListener {
                uploadImageFailed.postValue(it.message.toString())
            }
    }

    fun retrievePhoto() {
        val storageReference =
            FirebaseStorage.getInstance().reference.child("userImages/" + userAuth.currentUser.uid)
        storageReference.downloadUrl.addOnSuccessListener { imageUri ->
            Log.d(TAG, "ImageUpload:Success")
            userpref.image = imageUri.toString()
            retriveImage.postValue(imageUri)
        }
            .addOnFailureListener {
                Log.d(TAG, it.toString())
            }

    }


    fun deleteProfileImage() {
        val storageReference =
            FirebaseStorage.getInstance().reference.child("userImages/" + userAuth.currentUser.uid)
        storageReference.delete().addOnSuccessListener {
            Log.d(TAG, "ImageUpload:Success")
        }
            .addOnFailureListener {
                Log.d(TAG, it.toString())
            }


    }



    companion object {
        private const val TAG = "EmailPassword"
    }

}