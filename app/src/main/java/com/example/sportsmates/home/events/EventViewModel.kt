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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EventViewModel : ViewModel() {
    private var listOfEvents: MutableList<Event>? = mutableListOf()
    var retriveEventSucess = MutableLiveData<List<Event>?>()
    var retriveEventError = MutableLiveData<String>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val events = getRelatedEvents(getUserSportsList())
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
        listOfSports: MutableList<String>?
    ): List<Event>? {
        FirebaseDatabase.getInstance().getReference("Event").get().addOnSuccessListener { data ->
            val children = data.children
            children.forEach { it ->
                var event: Event? = it.getValue(Event::class.java)
                //  listOfSCoaches = listOfSports?.filter { it in coach?.sportName!! }
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
