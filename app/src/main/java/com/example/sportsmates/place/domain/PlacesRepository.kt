package com.example.sportsmates.place.domain

import com.example.sportsmates.place.data.Place
import com.google.firebase.storage.StorageReference

interface PlacesRepository {
    suspend fun getPlaceListOfImages(placeId: String?): List<StorageReference>
    suspend fun getPlaceMainImage(placeId: String?): String
    suspend fun getPlaces(): List<Place?>
}