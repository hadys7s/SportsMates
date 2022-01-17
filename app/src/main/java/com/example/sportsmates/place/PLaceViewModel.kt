package com.example.sportsmates.place

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PLaceViewModel : ViewModel() {

    private val _listOfSPlacesEvent = MutableLiveData<List<Place?>>()
    val listOfSPlacesEvent: MutableLiveData<List<Place?>> get() = _listOfSPlacesEvent

    private val _listOfPlacesImagesEvent = MutableLiveData<MutableList<StorageReference>>()
    val listOfPlacesImagesEvent: MutableLiveData<MutableList<StorageReference>> get() = _listOfPlacesImagesEvent


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val places = getRelatedCoaches()
            places.forEach { place ->
                place?.imageList = retrievePhoto(place?.placeId)
            }
            _listOfSPlacesEvent.postValue(places)
        }
    }


    private suspend fun getRelatedCoaches(): List<Place?> {
        val listOfSPlaces: MutableList<Place?> = mutableListOf()
        FirebaseDatabase.getInstance().getReference("Place").get().addOnSuccessListener { data ->
            val children = data.children
            children.forEach { it ->
                val place: Place? = it.getValue(Place::class.java)
                listOfSPlaces.add(place)
            }
        }.await()
        return listOfSPlaces
    }

    suspend fun retrievePhoto(placeId: String?): MutableList<StorageReference>? {
        var listOfPlacesImages: MutableList<StorageReference>? = mutableListOf()

        val listReference =
            FirebaseStorage.getInstance().reference.child("/place/$placeId")

        listReference.listAll().addOnSuccessListener { listResult ->
            Log.d(ContentValues.TAG, listResult.items.toString())
            listOfPlacesImages = listResult.items
        }.await()

        return listOfPlacesImages


    }

    fun retrievePhotoDetails(placeId: String?) {
        val listReference =
            FirebaseStorage.getInstance().reference.child("/place/$placeId")

        listReference.listAll().addOnSuccessListener { listResult ->
            Log.d(ContentValues.TAG, listResult.items.toString())
            _listOfPlacesImagesEvent.postValue(listResult.items)
        }
    }
}