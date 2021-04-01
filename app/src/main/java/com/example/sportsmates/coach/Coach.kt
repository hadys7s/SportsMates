package com.example.sportsmates.coach

import android.net.Uri
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