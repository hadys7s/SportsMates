package com.example.sportsmates.home.domain.usecases

import com.example.sportsmates.home.data.datamodels.EventDataItem
import com.example.sportsmates.home.domain.datainterfaces.EventsRepository
import com.example.sportsmates.auth.data.repo.UserRepository1

class EventsUseCase(
    private val eventsRepository: EventsRepository,
    private val userRepository: UserRepository1
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