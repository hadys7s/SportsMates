package com.example.sportsmates.coach

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.signUp.data.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CoachViewModel() : ViewModel() {

    private var listOfSCoaches: MutableList<Coach>? = mutableListOf()
    val _listOfSCoachesEvent = MutableLiveData<List<Coach>?>()
    var _listOfSCoachesimagesEvent = MutableLiveData<MutableList<StorageReference>?>()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val coaches = getRelatedCoaches(getUserSportsList())
            coaches?.forEach { coach ->
                coach.imageList = retrievePhoto(coach.coachId)
            }
            _listOfSCoachesEvent.postValue(coaches)

        }

    }


    private suspend fun getRelatedCoaches(
        listOfSports: MutableList<String>?
    ): List<Coach>? {
        FirebaseDatabase.getInstance().getReference("Coach").get().addOnSuccessListener { data ->
            val children = data.children
            children.forEach { it ->
                var coach: Coach? = it.getValue(Coach::class.java)
                //  listOfSCoaches = listOfSports?.filter { it in coach?.sportName!! }
                if (listOfSports!!.contains(coach?.sportName)) {
                    listOfSCoaches?.add(coach!!)
                }
            }


        }.await()
        return listOfSCoaches
    }

    suspend fun retrievePhoto(coachId: String?): MutableList<StorageReference>? {
        var _listOfSCoachesimages: MutableList<StorageReference>? = mutableListOf()

        val listReference =
            FirebaseStorage.getInstance().reference.child("/coach/$coachId")

        listReference.listAll().addOnSuccessListener { listResult ->
            Log.d(TAG, listResult.items.toString())
            _listOfSCoachesimages = listResult.items
        }.await()

        return _listOfSCoachesimages


    }


     fun retrievePhotoDetails(coachId: String?) {
        val listReference =
            FirebaseStorage.getInstance().reference.child("/coach/$coachId")

        listReference.listAll().addOnSuccessListener { listResult ->
            Log.d(TAG, listResult.items.toString())
            _listOfSCoachesimagesEvent.postValue( listResult.items)
        }


    }



    private suspend fun getUserSportsList(): MutableList<String>? {
        var listOfSports: MutableList<String>? = mutableListOf()
        FirebaseDatabase.getInstance().getReference("Users")
            .child(Firebase.auth.currentUser.uid)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "getUser:Success")
                    val user: User? = task.result?.getValue(User::class.java)
                    listOfSports = user?.sportsList?.toMutableList()

                } else {
                    Log.d(TAG, "getUser:Failed", task.exception)
                }
            }.await()
        return listOfSports
    }

}
