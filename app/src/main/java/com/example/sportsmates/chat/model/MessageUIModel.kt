package com.example.sportsmates.chat.model

import android.net.Uri

data class MessageUIModel(
    var userId: String? = "",
    var message: String? = "",
    var time: String? = "",
    var userName: String? = "",
    var userImage: Uri? = null
)

fun MessageUIModel.toMessageUiModel(
    userName: String,
    userImage: Uri? = null
): MessageUIModel {
    return MessageUIModel(
        message = this.message,
        time = this.time,
        userId = this.userId,
        userName = userName,
        userImage = userImage
    )
}