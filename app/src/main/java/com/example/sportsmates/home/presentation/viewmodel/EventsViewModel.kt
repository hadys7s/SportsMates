package com.example.sportsmates.home.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.home.data.datamodels.EventDataItem
import com.example.sportsmates.home.domain.usecases.EventsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsViewModel(val eventsUseCase: EventsUseCase) : ViewModel() {
    val listOfEventDataItems = MutableLiveData<List<EventDataItem?>>()
    val retriveEventError = MutableLiveData<String>()

    init {
        getEvents()
    }

    private fun getEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val events = eventsUseCase.getRelatedEvents()
                listOfEventDataItems.postValue(events)
            } catch (ex: Exception) {
                retriveEventError.postValue(ex.message.toString())
            }
        }
    }
}
