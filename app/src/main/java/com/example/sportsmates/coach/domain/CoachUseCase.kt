package com.example.sportsmates.coach.domain

import com.example.sportsmates.auth.domain.datainterfaces.UserRepository
import com.example.sportsmates.coach.data.Coach
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class CoachUseCase(
    private val userPreferences: UserRepository,
    private val coachesRepository: CoachesRepository
) {
    suspend fun getRelatedCoaches(): List<Coach?> {
        val sportsList = userPreferences.getCashedUser()?.sportsList ?: emptyList()
        val coachesList = coachesRepository.getAllCoaches(sportsList)
        val filteredCoaches = coachesList.filter { coach ->
            sportsList.contains(coach?.sportName)
        }.map { coach ->
            coroutineScope {
                async {
                    coach?.mainImage = coachesRepository.getCoachMainImage(coach?.coachId)
                    coach
                }
            }
        }
        return filteredCoaches.awaitAll()
    }

    suspend fun getCoachListOfImages(coachId:String?) = coachesRepository.getCoachImages(coachId)

}