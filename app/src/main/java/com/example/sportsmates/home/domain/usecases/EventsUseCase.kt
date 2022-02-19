package com.example.sportsmates.home.domain.usecases

import com.example.sportsmates.UserPreferences
import com.example.sportsmates.auth.domain.datainterfaces.UserRepository
import com.example.sportsmates.home.data.datamodels.EventDataItem
import com.example.sportsmates.home.domain.datainterfaces.EventsRepository

class EventsUseCase(
    private val eventsRepository: EventsRepository,
    val userRepository: UserRepository
) {
    suspend fun getRelatedEvents(): List<EventDataItem?> {
        val events = eventsRepository.getAllEvents()
        val sports = userRepository.getCashedUser()?.sportsList ?: emptyList()
        val filteredEvents = events.filter { eventDataItem ->
            sports.contains(eventDataItem?.sport)
        }
            .map { eventDataItem ->
                eventDataItem?.img = eventsRepository.getEventPhoto(eventDataItem?.eventId)
                eventDataItem
            }
        return filteredEvents
    }
}