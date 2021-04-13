package com.example.sportsmates.news.data.model

import com.example.sportsmates.news.NewsItem
import com.google.gson.annotations.SerializedName

data class NewsListResponse(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<NewsItem>
)
