package com.example.sportsmates.coach.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoachUiModel(
    val name: String? = "",
    val sportName: String? = "",
    val coachId: String? = "",
    val email: String? = "",
    val about: String? = "",
    val pricePerHour: String? = "",
    val address: String? = ""
) : Parcelable