package com.example.sportsmates.coach.domain

import com.example.sportsmates.UserPreferences
import com.example.sportsmates.coach.data.Coach

class CoachUseCase(
    val userPreferences: UserPreferences,
    private val coachesRepository: CoachesRepository
) {
    suspend fun getRelatedCoaches(): List<Coach?> {
        val sportsList = userPreferences.user?.sportsList ?: emptyList()
        val coachesList = coachesRepository.getAllCoaches(sportsList)
        val filteredCoaches = coachesList.filter { coach ->
            sportsList.contains(coach?.sportName)
        }.map { coach ->
            coach?.mainImage = coachesRepository.getCoachMainImage(coach?.coachId)
            coach
        }
        return filteredCoaches
    }

    suspend fun getCoachListOfImages(coachId:String?) = coachesRepository.getCoachImages(coachId)

}