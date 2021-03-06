package com.example.sportsmates.home.domain.usecases

import com.example.sportsmates.home.data.datamodels.EventDataItem
import com.example.sportsmates.home.domain.datainterfaces.EventsRepository
import com.example.sportsmates.signUp.data.repo.UserRepository

class EventsUseCase(
    private val eventsRepository: EventsRepository,
    private val userRepository: UserRepository
) {
    suspend fun getRelatedEvents(): List<EventDataItem?> {
        val events = eventsRepository.getRelatedEvents()
        val filteredEvents: List<EventDataItem?>
        val sports = userRepository.getUserSportsList()
        filteredEvents = events.filter { eventDataItem ->
            sports.contains(eventDataItem?.sport)
        }
        filteredEvents.map { eventDataItem ->
            eventDataItem?.img = eventsRepository.getEventPhoto(eventDataItem?.eventId)
        }
        return filteredEvents
    }
}