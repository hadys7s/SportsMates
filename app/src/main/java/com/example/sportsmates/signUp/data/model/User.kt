package com.example.sportsmates.signUp.data.model

import android.net.Uri
import android.os.Parcelable
import com.example.sportsmates.chat.model.MessageModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: String? = "",
    var name: String? = "",
    var password: String? = "",
    var email: String? = "",
    val about: String? = "",
    var age: String? = "",
    var gender: String? = "",
    var city: String? = "",
    var sportsList: List<String> = listOf(),
    var userImage: Uri? = null
) : Parcelable

fun User.toMessageUiModel(): MessageModel {
    return MessageModel(
        userId = this.id,
        userName = this.name,
        userImage = this.userImage.toString()
    )
}
