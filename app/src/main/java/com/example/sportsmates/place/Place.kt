package com.example.sportsmates.place

import com.google.firebase.storage.StorageReference

data class Place(
    val name: String? = "",
    val open: String? = "",
    val close: String? = "",
    val placeId: String? = "",
    val email: String? = "",
    val about: String? = "",
    val pricePerHour: String? = "",
    val address: String? = "",
    val placeType: String? = "",
    var imageList: List<StorageReference>? = listOf()
)

fun Place.toUiModel(): PlaceUiModel {
    return PlaceUiModel(
        name = this.name,
        open = this.open,
        close = this.close,
        about = this.about,
        pricePerHour = this.pricePerHour,
        address = this.address,
        email = this.email,
        placeId = this.placeId,
        placeType = this.placeType
    )
}

