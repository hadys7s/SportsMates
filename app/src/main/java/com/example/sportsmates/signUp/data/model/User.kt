package com.example.sportsmates.signUp.data.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.example.sportsmates.chat.model.MessageModel


data class User(
    var id: String? = "",
    var name: String? = "",
    var password: String? = "",
    var email: String? = "",
    val phoneNumber: String? = "",
    var age: String? = "",
    var gender: String? = "",
    var city: String? = "",
    var sportsList: List<String>? = listOf(),
    var userImage: Uri? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readParcelable(Uri::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(password)
        parcel.writeString(email)
        parcel.writeString(phoneNumber)
        parcel.writeString(age)
        parcel.writeString(gender)
        parcel.writeString(city)
        parcel.writeStringList(sportsList)
        parcel.writeParcelable(userImage, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

fun User.toMessageModel(): MessageModel {
    return MessageModel(
        userId = this.id,
        userName = this.name,
        userImage = this.userImage.toString()
    )
}
