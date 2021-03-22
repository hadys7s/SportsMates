package com.example.sportsmates.SignUp.data.model

import com.google.gson.annotations.SerializedName


data class User (
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("email")
    val email: String?= "",
    @SerializedName("phoneNumber")
    val phoneNumber: String?= "",
    @SerializedName("age")
    val age: String? = "",
    @SerializedName("gender")
    val gender: String? = "",
    @SerializedName("city")
    val city: String? = "",
    @SerializedName("sports")
    val sportsList: List<String>? = listOf()
)