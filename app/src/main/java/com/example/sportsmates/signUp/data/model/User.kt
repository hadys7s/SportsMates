package com.example.sportsmates.signUp.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class User(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("password")
    var password: String? = "",
    @SerializedName("phoneNumber")
    var email: String? = "",
    @SerializedName("phoneNumber")
    val phoneNumber: String? = "",
    @SerializedName("age")
    var age: String? = "",
    @SerializedName("gender")
    var gender: String? = "",
    @SerializedName("city")
    var city: String? = "",
    @SerializedName("sports")
    var sportsList: List<String>? = listOf()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(password)
        parcel.writeString(email)
        parcel.writeString(phoneNumber)
        parcel.writeString(age)
        parcel.writeString(gender)
        parcel.writeString(city)
        parcel.writeStringList(sportsList)
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