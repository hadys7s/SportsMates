package com.example.sportsmates.place.domain

import com.example.sportsmates.UserPreferences
import com.example.sportsmates.auth.domain.datainterfaces.UserRepository
import com.example.sportsmates.place.data.Place
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.last

class PlacesUseCase(
    private val userRepository: UserRepository,
    private val placesRepository: PlacesRepository
) {
    suspend fun getRelatedPlaces(): List<Place?> {
        val userLocation = userRepository.getUserInfo().last()?.city
        val places = placesRepository.getAllPlaces()
        val filteredPlaces =
            places.filter { place ->
                place?.city == userLocation
            }.map { place ->
                coroutineScope {
                    async {
                        place?.mainImage = placesRepository.getPlaceMainImage(place?.placeId)
                        place
                    }
                }
            }
        return filteredPlaces.awaitAll()
    }

    suspend fun getPlaceListOfImages(placeId: String?): List<StorageReference?> =
        placesRepository.getPlaceListOfImages(placeId)
}