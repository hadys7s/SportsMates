package com.example.sportsmates.places

import android.os.Parcelable

data class Place(
    val name: String? = "",
    val email: String? = "",
    val about: String? = "",
    val opensAt: String? = "",
    val closedAt: String? = "",
    val pricePerHour: String? = "",
    val address: String? = "",
    val imageList: List<String>

)