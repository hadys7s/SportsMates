package com.example.sportsmates.place.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.networking.Resource
import com.example.sportsmates.place.data.Place
import com.example.sportsmates.place.domain.PlacesUseCase
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PLaceViewModel(private val placesUseCase: PlacesUseCase) : ViewModel() {

    private val _listOfSPlacesEvent = MutableStateFlow<Resource<List<Place?>>>(Resource.Loading)
    val listOfSPlacesEvent get() = _listOfSPlacesEvent.asStateFlow()

    private val _listOfPlacesImagesEvent =
        MutableStateFlow<Resource<List<StorageReference?>>>(Resource.Loading)
    val listOfPlacesImagesEvent get() = _listOfPlacesImagesEvent.asStateFlow()


    init {
        viewModelScope.launch {
            try {
                _listOfSPlacesEvent.emit(
                    Resource.Success(data = placesUseCase.getRelatedPlaces())
                )
            } catch (throwable: Throwable) {
                _listOfSPlacesEvent.emit(Resource.Error(throwable = throwable))
            }
        }
    }

    suspend fun getPlacesListOfImages(placeId: String?) {
        try {
            _listOfPlacesImagesEvent.emit(
                Resource.Success(
                    data = placesUseCase.getPlaceListOfImages(
                        placeId
                    )
                )
            )

        } catch (throwable: Throwable) {
            _listOfPlacesImagesEvent.emit(Resource.Error(throwable = throwable))

        }
    }
}