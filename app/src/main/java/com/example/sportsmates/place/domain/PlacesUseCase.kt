package com.example.sportsmates.place.domain

import com.example.sportsmates.UserPreferences
import com.example.sportsmates.place.data.Place
import com.google.firebase.storage.StorageReference

class PlacesUseCase(
    private val userPreferences: UserPreferences,
    private val placesRepository: PlacesRepository
) {
    suspend fun getRelatedPlaces(): List<Place?> {
        val userLocation = userPreferences.user?.city
        val places = placesRepository.getPlaces()
        val filteredPlaces =
            places.filter { place ->
                place?.city == userLocation
            }.map { place ->
            place?.mainImage = placesRepository.getPlaceMainImage(place?.placeId)
                place
        }
        return filteredPlaces
    }

    suspend fun getPlaceListOfImages(placeId: String?): List<StorageReference?> =
        placesRepository.getPlaceListOfImages(placeId)
}