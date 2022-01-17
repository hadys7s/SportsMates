package com.example.sportsmates.home.events

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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EventViewModel : ViewModel() {
    private var listOfEvents: MutableList<Event>? = mutableListOf()
    var retriveEventSucess = MutableLiveData<List<Event>?>()
    var retriveEventError = MutableLiveData<String>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val listOfSports: Deferred<List<String>?> = async { getUserSportsList() }
                val events = getRelatedEvents(listOfSports.await())
                if (!events.isNullOrEmpty()) {
                    events.forEach { event ->
                        event.img = reteriveUserPhoto(event.eventId)
                    }
                    retriveEventSucess.postValue(events)
                }
            } catch (e: Exception) {
            }
        }
    }

    private suspend fun getRelatedEvents(
        listOfSports: List<String>?
    ): List<Event>? {
        FirebaseDatabase.getInstance().getReference("Event").get().addOnSuccessListener { data ->
            val events = data.children
            events.forEach { it ->
                val event: Event? = it.getValue(Event::class.java)
                if (listOfSports!!.contains(event?.sport)) {
                    listOfEvents?.add(event!!)
                }
            }
            if (listOfEvents.isNullOrEmpty()) {
                retriveEventError.postValue("There is No Event For The Sport You Are interested in ")
            }


        }.addOnFailureListener {
            retriveEventError.postValue(it.toString())
        }.await()
        return listOfEvents
    }

    private suspend fun getUserSportsList(): List<String>? {
        var listOfSports: List<String>? = listOf()
        FirebaseDatabase.getInstance().getReference("Users")
            .child(Firebase.auth.currentUser.uid)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "getUser:Success")
                    val user: User? = task.result?.getValue(User::class.java)
                    listOfSports = user?.sportsList
                } else {
                    Log.d(ContentValues.TAG, "getUser:Failed", task.exception)
                }
            }.await()

        return listOfSports
    }

    private suspend fun reteriveUserPhoto(eventId: String?): Uri? {
        var eventImage: Uri? = null
        val storageReference = FirebaseStorage.getInstance().reference.child("event/$eventId.jpg")
        storageReference.downloadUrl.addOnSuccessListener { imgList ->
            eventImage = imgList
        }.addOnFailureListener {
            retriveEventError.postValue(it.message.toString())
        }.await()
        return eventImage
    }

}
