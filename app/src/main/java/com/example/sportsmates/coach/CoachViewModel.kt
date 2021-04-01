package com.example.sportsmates.coach

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.signUp.data.Repo.UserRepository
import com.example.sportsmates.signUp.data.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait

class CoachViewModel() : ViewModel()  {

    private var listOfSCoaches: MutableList<Coach>? = mutableListOf()
    val _listOfSCoachesEvent = MutableLiveData<List<Coach>?>()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            _listOfSCoachesEvent.postValue(getRelatedCoaches(getUserSportsList()))


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
                    coach?.imageList = retrievePhoto(coach?.coachId)
                    listOfSCoaches?.add(coach!!)

                }
            }


        }.await()
        return listOfSCoaches
    }

    suspend fun retrievePhoto(coachId: String?): List<StorageReference>? {
        var _listOfSCoachesimages: List<StorageReference>? = listOf()

        val listReference =
            FirebaseStorage.getInstance().reference.child("/coach/$coachId")

        listReference.listAll().addOnSuccessListener { listResult ->
            Log.d(TAG, listResult.items.toString())
            _listOfSCoachesimages = listResult.items
        }.await()



        return _listOfSCoachesimages

    }

    private suspend fun getUserSportsList(): MutableList<String>? {
        var listOfSports: MutableList<String>? = mutableListOf()
        FirebaseDatabase.getInstance().getReference("Users")
            .child(Firebase.auth.currentUser.uid)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "getUser:Success")
                    val user: User? = task.result?.getValue(User::class.java)
                    listOfSports = user?.sportsList

                } else {
                    Log.d(TAG, "getUser:Failed", task.exception)
                }
            }.await()

        return listOfSports
    }


}