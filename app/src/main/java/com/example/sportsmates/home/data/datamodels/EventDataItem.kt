package com.example.sportsmates.home.data.datamodels

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventDataItem(
    var eventId: String? = "",
    var name: String? = "",
    var description: String? = "",
    var date: String? = "",
    var place: String? = "",
    var sport: String? = "",
    var start: String? = "",
    var finish: String? = "",
    var ticketPrice: String? = "",
    var img: Uri? = null
) : Parcelable