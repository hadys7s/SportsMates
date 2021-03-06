package com.example.sportsmates.discover

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.signUp.data.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ContactsViewModel : ViewModel() {
    private var _listOfUsers: MutableList<User>? = mutableListOf()
    private var _listOfUsersInTheSameCity: MutableList<User>? = mutableListOf()
    var retriveUsersSuccess = MutableLiveData<List<User>>()
    var retriveUsersError = MutableLiveData<String>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                filterUserByCity()
                val users = getRelatedUsers(getUserSportsList())
                if (!users.isNullOrEmpty()) {
                    users.forEach { user ->
                        user.userImage = retriveUserPhoto(user)
                    }
                    retriveUsersSuccess.postValue(users!!)
                }
            } catch (e: Exception) {
            }

        }

    }

    private fun getRelatedUsers(listOfSports: List<String>?): MutableList<User>? {
        _listOfUsersInTheSameCity?.forEach { user ->
            val otherUserSportsList = user.sportsList.toList()
            for (sport in listOfSports!!) {
                if (otherUserSportsList.contains(sport)) {
                    _listOfUsers!!.add(user)
                }
                break
            }

        }
        if (_listOfUsers.isNullOrEmpty()) {

            retriveUsersError.postValue("Nobody is interested in your sports")
            return null
        } else {
            return _listOfUsers
        }
    }

    private suspend fun filterUserByCity(
    ): List<User>? {
        var currentUserCity: String? = ""
        FirebaseDatabase.getInstance().getReference("Users")
            .child(Firebase.auth.currentUser.uid).get().addOnCompleteListener { task ->
                val currentUser: User? = task.result?.getValue(User::class.java)
                currentUserCity = currentUser!!.city
            }
        FirebaseDatabase.getInstance().getReference("Users").get()
            .addOnSuccessListener { data ->
                val children = data.children
                children.forEach {
                    val user: User? = it.getValue(User::class.java)
                    if (user!!.city!!.equals(currentUserCity) && user!!.id != Firebase.auth.currentUser.uid)
                        _listOfUsersInTheSameCity?.add(user)
                }
            }.addOnFailureListener {
                retriveUsersError.postValue(it.message.toString())
            }.await()
        return _listOfUsersInTheSameCity
    }

    private suspend fun getUserSportsList(): MutableList<String>? {
        var listOfSports: MutableList<String>? = mutableListOf()
        FirebaseDatabase.getInstance().getReference("Users")
            .child(Firebase.auth.currentUser.uid)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "getUser:Success")
                    val user: User? = task.result?.getValue(User::class.java)
                    listOfSports = user?.sportsList?.toMutableList()

                } else {
                    Log.d(ContentValues.TAG, "getUser:Failed", task.exception)
                    retriveUsersError.postValue("getUser:Failed")
                }
            }.await()

        return listOfSports
    }

    private suspend fun retriveUserPhoto(user: User): Uri? {
        var userImage: Uri? = null
        val storageReference = FirebaseStorage.getInstance().reference.child("userImages/" + user.id)
        storageReference.downloadUrl.addOnSuccessListener { imgUrl ->
            userImage = imgUrl
        }.addOnFailureListener {
            retriveUsersError.postValue(it.message.toString())
        }.await()
        return userImage
    }
}
