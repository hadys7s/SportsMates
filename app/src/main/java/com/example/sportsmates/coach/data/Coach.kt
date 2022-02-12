package com.example.sportsmates.coach.data

import android.net.Uri
import com.example.sportsmates.coach.presentation.CoachUiModel

data class Coach(
    val name: String? = "",
    val sportName: String? = "",
    val coachId: String ="",
    val email: String? = "",
    val about: String? = "",
    val pricePerHour: String? = "",
    val address: String? = "",
    var mainImage: Uri? = null
)

fun Coach.toUiModel(): CoachUiModel {
    return CoachUiModel(
        name = this.name,
        sportName = this.sportName,
        coachId = this.coachId,
        about = this.about,
        pricePerHour = this.pricePerHour,
        address = this.address,
        email = this.email
    )
}


