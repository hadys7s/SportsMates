package com.example.sportsmates.home.data.datamodels

import com.google.gson.annotations.SerializedName

data class NewsListResponse(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<NewsDataItem>
)
