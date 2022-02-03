package com.example.sportsmates.coach

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.auth.data.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

typealias Coaches = List<Coach>
typealias CoachesImages = MutableList<StorageReference>

class CoachViewModel : ViewModel() {

    private var listOfSCoaches: MutableList<Coach>? = mutableListOf()


    private val _listOfSCoachesEvent = MutableLiveData<Coaches>()
    val listOfSCoachesEvent: LiveData<Coaches> get() = _listOfSCoachesEvent
    private val _listOfCoachesImagesEvent = MutableLiveData<CoachesImages>()
    val listOfSCoachesImagesEvent: LiveData<CoachesImages> get() = _listOfCoachesImagesEvent


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val coaches = getRelatedCoaches(getUserSportsList())
            coaches?.forEach { coach ->
                coach.imageList = retrievePhoto(coach.coachId)
            }
            _listOfSCoachesEvent.postValue(coaches ?: emptyList())
        }
    }


    private suspend fun getRelatedCoaches(
        listOfSports: MutableList<String>?
    ): List<Coach>? {
        FirebaseDatabase.getInstance().getReference("Coach").get().addOnSuccessListener { data ->
            val children = data.children
            children.forEach { it ->
                val coach: Coach? = it.getValue(Coach::class.java)
                if (listOfSports!!.contains(coach?.sportName)) {
                    listOfSCoaches?.add(coach!!)
                }
            }
        }.await()
        return listOfSCoaches
    }

    private suspend fun retrievePhoto(coachId: String?): MutableList<StorageReference>? {
        var listOfSCoachesImages: MutableList<StorageReference>? = mutableListOf()

        val listReference =
            FirebaseStorage.getInstance().reference.child("/coach/$coachId")

        listReference.listAll().addOnSuccessListener { listResult ->
            Log.d(TAG, listResult.items.toString())
            listOfSCoachesImages = listResult.items
        }.await()

        return listOfSCoachesImages


    }


    fun retrievePhotoDetails(coachId: String?) {
        val listReference =
            FirebaseStorage.getInstance().reference.child("/coach/$coachId")

        listReference.listAll().addOnSuccessListener { listResult ->
            Log.d(TAG, listResult.items.toString())
            _listOfCoachesImagesEvent.postValue(listResult.items)
        }


    }


    private suspend fun getUserSportsList(): MutableList<String>? {
        var listOfSports: MutableList<String>? = mutableListOf()
        Firebase.auth.currentUser?.let {
            FirebaseDatabase.getInstance().getReference("Users")
                .child(it.uid)
                .get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "getUser:Success")
                        val user: User? = task.result?.getValue(User::class.java)
                        listOfSports = user?.sportsList?.toMutableList()

                    } else {
                        Log.d(TAG, "getUser:Failed", task.exception)
                    }
                }.await()
        }
        return listOfSports
    }

}
