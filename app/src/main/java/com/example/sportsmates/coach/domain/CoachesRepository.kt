package com.example.sportsmates.coach.domain

import android.net.Uri
import com.example.sportsmates.coach.data.Coach
import com.example.sportsmates.coach.presentation.Coaches
import com.google.firebase.storage.StorageReference

interface CoachesRepository {
    suspend fun getCoachImages(coachId: String?): List<StorageReference?>
    suspend fun getCoachMainImage(coachId: String?): Uri?
    suspend fun getAllCoaches(listOfSports: List<String>?): List<Coach?>

}