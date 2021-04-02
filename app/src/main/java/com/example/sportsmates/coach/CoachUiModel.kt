package com.example.sportsmates.coach

import android.os.Parcel
import android.os.Parcelable

data class CoachUiModel(
    val name: String? = "",
    val sportName: String? = "",
    val coachId: String? = "",
    val email: String? = "",
    val about: String? = "",
    val pricePerHour: String? = "",
    val address: String? = ""
) :Parcelable {
    constructor(parcel: Parcel) : this(
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
        parcel.writeString(sportName)
        parcel.writeString(coachId)
        parcel.writeString(email)
        parcel.writeString(about)
        parcel.writeString(pricePerHour)
        parcel.writeString(address)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CoachUiModel> {
        override fun createFromParcel(parcel: Parcel): CoachUiModel {
            return CoachUiModel(parcel)
        }

        override fun newArray(size: Int): Array<CoachUiModel?> {
            return arrayOfNulls(size)
        }
    }
}
