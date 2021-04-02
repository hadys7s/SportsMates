package com.example.sportsmates.coach

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.storage.StorageReference
import java.io.Serializable

data class Coach(
    val name: String? = "",
    val sportName: String? = "",
    val coachId: String? = "",
    val email: String? = "",
    val about: String? = "",
    val pricePerHour: String? = "",
    val address: String? = "",
    var imageList: List<StorageReference>? = listOf()

) :Serializable

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

