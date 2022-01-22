package com.example.sportsmates.home.news.presentation.uiModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class NewsItemUIModel(
    val website: String?,
    val title: String?,
    val imageUrl: String?,
    val publishedAt: String?,
    val content: String?
) : Parcelable