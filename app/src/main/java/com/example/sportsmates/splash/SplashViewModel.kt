package com.example.sportsmates.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.signUp.data.Repo.UserRepository
import com.example.sportsmates.utils.SingleLiveEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class SplashViewModel(private val userRepository: UserRepository) : ViewModel() {
    val authenticationNavigationEvent = SingleLiveEvent<Any>()
    val homeNavigationEvent = SingleLiveEvent<Any>()


    fun checkCurrentUserAuthorization() {
        if (Firebase.auth.currentUser != null) {
            FirebaseDatabase.getInstance().getReference("Users")
                .child(Firebase.auth.currentUser.uid).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // This callback will fire even if the node doesn't exist, so now check for existence
                        if (dataSnapshot.exists()) {
                            homeNavigationEvent.call()
                        } else {
                            authenticationNavigationEvent.call()
                            userRepository.deleteUser()
                            userRepository.deleteProfileImage()

                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                }
                )         // [END check_current_user]
        } else {
            authenticationNavigationEvent.call()

        }
    }
}
