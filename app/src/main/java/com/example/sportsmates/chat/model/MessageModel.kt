package com.example.sportsmates.chat.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageModel(
    var userId: String? = "",
    var message: String? = "",
    var time: String? = "",
    var userName: String? = "",
    var userImage: String? = null
) : Parcelable