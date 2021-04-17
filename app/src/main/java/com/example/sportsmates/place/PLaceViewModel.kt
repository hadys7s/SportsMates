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

class PLaceViewModel() : ViewModel() {

    private var listOfSPlaces: MutableList<Place>? = mutableListOf()
    val _listOfSPlacesEvent = MutableLiveData<List<Place>?>()
    var _listOfPlacesImagesEvent = MutableLiveData<MutableList<StorageReference>?>()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val places = getRelatedCoaches()
            places?.forEach { place ->
                place.imageList = retrievePhoto(place.placeId)
            }
            _listOfSPlacesEvent.postValue(places)
        }
    }


    private suspend fun getRelatedCoaches(): List<Place>? {
        FirebaseDatabase.getInstance().getReference("Place").get().addOnSuccessListener { data ->
            val children = data.children
            children.forEach { it ->
                var place: Place? = it.getValue(Place::class.java)
                    listOfSPlaces?.add(place!!)
            }


        }.await()
        return listOfSPlaces
    }

    suspend fun retrievePhoto(placeId: String?): MutableList<StorageReference>? {
        var _listOfPlacesimages: MutableList<StorageReference>? = mutableListOf()

        val listReference =
            FirebaseStorage.getInstance().reference.child("/place/$placeId")

        listReference.listAll().addOnSuccessListener { listResult ->
            Log.d(ContentValues.TAG, listResult.items.toString())
            _listOfPlacesimages = listResult.items
        }.await()

        return _listOfPlacesimages


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