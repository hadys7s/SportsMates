package com.example.sportsmates.home.data.repositories

import android.net.Uri
import com.example.sportsmates.home.data.datamodels.EventDataItem
import com.example.sportsmates.home.domain.datainterfaces.EventsRepository
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class EventsRepositoryImpl : EventsRepository {

    override suspend fun getRelatedEvents(): List<EventDataItem?> {
        val listOfEvents: MutableList<EventDataItem?> = mutableListOf()
        FirebaseDatabase.getInstance().getReference("Event").get().addOnSuccessListener { data ->
            val events = data.children
            events.map {
                listOfEvents.add(it.getValue(EventDataItem::class.java))
            }
        }.await()
        return listOfEvents
    }

    override suspend fun getEventPhoto(eventId: String?): Uri? {
        var eventImage: Uri? = null
        val storageReference = FirebaseStorage.getInstance().reference.child("event/$eventId.jpg")
        storageReference.downloadUrl.addOnSuccessListener { imgList ->
            eventImage = imgList
        }.await()
        return eventImage
    }
}