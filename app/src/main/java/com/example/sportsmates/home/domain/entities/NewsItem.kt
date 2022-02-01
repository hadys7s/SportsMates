package com.example.sportsmates.home.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class NewsItem(
    val website: String?,
    val title: String?,
    val imageUrl: String?,
    val publishedAt: String?,
    val content: String?
) : Parcelable