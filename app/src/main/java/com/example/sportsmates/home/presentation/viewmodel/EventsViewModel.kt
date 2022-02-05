package com.example.sportsmates.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.home.data.datamodels.EventDataItem
import com.example.sportsmates.home.domain.usecases.EventsUseCase
import com.example.sportsmates.networking.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventsViewModel(private val eventsUseCase: EventsUseCase) : ViewModel() {
    private val _listOfEventDataItems =
        MutableStateFlow<Resource<List<EventDataItem?>>>(Resource.Loading)
    val listOfEventDataItems get() = _listOfEventDataItems.asStateFlow()

    init {
        getEvents()
    }

    private fun getEvents() {
        viewModelScope.launch {
            try {
                _listOfEventDataItems.emit(Resource.Success(data = eventsUseCase.getRelatedEvents()))
            } catch (ex: Exception) {
                _listOfEventDataItems.emit(Resource.Error(exception = ex))
            }
        }
    }
}
