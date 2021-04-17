package com.example.sportsmates.home.events

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class Event(
    var eventId: String? = "",
    var name: String? = "",
    var description: String? = "",
    var date: String? = "",
    var place: String? = "",
    var sport: String? = "",
    var start: String? = "",
    var finish: String? = "",
    var ticketPrice: String? = "",
    var img :Uri?=null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Uri::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(eventId)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(date)
        parcel.writeString(place)
        parcel.writeString(sport)
        parcel.writeString(start)
        parcel.writeString(finish)
        parcel.writeString(ticketPrice)
        parcel.writeParcelable(img, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}