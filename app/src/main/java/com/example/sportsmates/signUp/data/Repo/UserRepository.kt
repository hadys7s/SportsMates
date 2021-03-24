package com.example.sportsmates.signUp.data.Repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.utils.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserRepository(
    private val userAuth: FirebaseAuth
) {

    var signUpSuccess = SingleLiveEvent<Any>()
    var signUpFailed = MutableLiveData<String>()
    var loginFailed = MutableLiveData<String>()
    var loginSuccess = SingleLiveEvent<Any>()


    fun login(email: String, password: String) {
        userAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // fetchUserData(userAuth.currentUser.uid)
                    loginSuccess.call()

                } else {
                    loginFailed.postValue(task.exception?.message)
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

    fun fetchUserData(): User? {
        var user: User? = User()
        FirebaseDatabase.getInstance().getReference("Users")
            .child(userAuth.currentUser.uid)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "getUser:Success")
                    user = task.result?.getValue(User::class.java)

                } else {
                    Log.d(TAG, "getUser:Failed", task.exception)

                }
            }
        return user
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

}