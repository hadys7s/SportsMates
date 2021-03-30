package com.example.sportsmates.coach

import android.os.Parcelable

data class Coach(
    val name: String? = "",
    val sportName: String? = "",
    val email: String? = "",
    val about: String? = "",
    val pricePerHour: String? = "",
    val address: String? = "",
    val imageList: List<String>

)