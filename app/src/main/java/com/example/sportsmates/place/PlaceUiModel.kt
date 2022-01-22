package com.example.sportsmates.place


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceUiModel(
    val name: String? = "",
    val open: String? = "",
    val close: String? = "",
    val placeId: String? = "",
    val email: String? = "",
    val about: String? = "",
    val pricePerHour: String? = "",
    val address: String? = "",
    val placeType: String? = ""
) : Parcelable