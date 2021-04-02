package com.example.sportsmates.place

import android.os.Parcel
import android.os.Parcelable

data class PlaceUiModel(
    val name: String? = "",
    val open: String? = "",
    val close: String? = "",
    val placeId: String? = "",
    val email: String? = "",
    val about: String? = "",
    val pricePerHour: String? = "",
    val address: String? = "",
    val placeType: String? = ""
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
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(open)
        parcel.writeString(close)
        parcel.writeString(placeId)
        parcel.writeString(email)
        parcel.writeString(about)
        parcel.writeString(pricePerHour)
        parcel.writeString(address)
        parcel.writeString(placeType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlaceUiModel> {
        override fun createFromParcel(parcel: Parcel): PlaceUiModel {
            return PlaceUiModel(parcel)
        }

        override fun newArray(size: Int): Array<PlaceUiModel?> {
            return arrayOfNulls(size)
        }
    }
}