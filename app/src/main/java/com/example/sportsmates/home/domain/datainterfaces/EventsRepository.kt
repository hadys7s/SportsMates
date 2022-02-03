package com.example.sportsmates.home.domain.datainterfaces

import android.net.Uri
import com.example.sportsmates.home.data.datamodels.EventDataItem

interface EventsRepository {
    suspend fun getRelatedEvents(): List<EventDataItem?>

    suspend fun getEventPhoto(eventId: String?): Uri?
}