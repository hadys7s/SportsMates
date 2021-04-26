package com.example.sportsmates.chat.model

import android.os.Parcel
import android.os.Parcelable

data class MessageModel(
    var userId: String? = "",
    var message: String? = "",
    var time: String? = "",
    var userName: String? = "",
    var userImage: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(message)
        parcel.writeString(time)
        parcel.writeString(userName)
        parcel.writeString(userImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageModel> {
        override fun createFromParcel(parcel: Parcel): MessageModel {
            return MessageModel(parcel)
        }

        override fun newArray(size: Int): Array<MessageModel?> {
            return arrayOfNulls(size)
        }
    }
}