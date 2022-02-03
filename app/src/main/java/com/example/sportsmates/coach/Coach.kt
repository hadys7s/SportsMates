package com.example.sportsmates.coach

import com.google.firebase.storage.StorageReference

data class Coach(
    val name: String? = "",
    val sportName: String? = "",
    val coachId: String? = "",
    val email: String? = "",
    val about: String? = "",
    val pricePerHour: String? = "",
    val address: String? = "",
    var imageList: List<StorageReference>? = listOf()
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


