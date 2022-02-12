package com.example.sportsmates.place.data


import android.net.Uri
import com.example.sportsmates.place.domain.PlacesRepository
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

class PlacesRepositoryImpl : PlacesRepository {

    override suspend fun getPlaceListOfImages(placeId: String?): List<StorageReference> {
        val listReference: MutableList<StorageReference> = mutableListOf()
        FirebaseStorage.getInstance().reference.child("/place/$placeId")
            .listAll().addOnSuccessListener { listResult ->
                listReference.addAll(listResult.items)
            }.await()
        return listReference
    }

    override suspend fun getPlaceMainImage(placeId: String?): Uri? {
        var uri: Uri? = null
        try {
            FirebaseStorage.getInstance().reference.child("/event/$placeId/main.jpg").downloadUrl.addOnSuccessListener {
                uri = it
            }.await()
        } catch (throwable: Throwable) {
        }
        return uri
    }

    override suspend fun getAllPlaces(): List<Place?> {
        val listOfSPlaces: MutableList<Place?> = mutableListOf()
        FirebaseDatabase.getInstance().getReference("Place").get().addOnSuccessListener { data ->
            val children = data.children
            children.forEach {
                val place: Place? = it.getValue(Place::class.java)
                listOfSPlaces.add(place)
            }
        }.await()
        return listOfSPlaces
    }
}