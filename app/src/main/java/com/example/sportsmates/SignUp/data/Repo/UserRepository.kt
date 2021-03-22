package com.example.sportsmates.SignUp.data.Repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sportsmates.SignUp.data.model.User
import com.example.sportsmates.Utils.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserRepository(
    private val userAuth: FirebaseAuth
) {

    var signUpSuccess = SingleLiveEvent<Any>()
    var signUpFailed = MutableLiveData<String>()
    var user = MutableLiveData<User>()
    var loginFailed = MutableLiveData<String>()
    var loginSuccess = SingleLiveEvent<Any>()


    fun login(email: String, password: String) {
        userAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // fetchUserData(userAuth.currentUser.uid)
                    loginSuccess.call()

                } else {
                    Log.w(TAG, "loginFailed:failure", task.exception)
                }

            }

    }

    fun signUp(user: User, password: String) {
        // [START create_user_with_email]
        userAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    addUserToDataBase(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                }
            }

        // [END create_user_with_email]
    }

    private fun addUserToDataBase(user: User) {
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

    fun fetchUserData(userId: String) {
        FirebaseDatabase.getInstance().getReference("Users")
            .child(userId)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "getUser:Success")
                    user.postValue(task.result?.getValue(User::class.java))

                } else {
                    loginFailed.postValue(task.exception?.message)
                    Log.d(TAG, "getUser:Failed", task.exception)

                }
            }

    }

    companion object {
        private const val TAG = "EmailPassword"
    }

}