package com.example.sportsmates.news

import com.example.sportsmates.news.presentation.model.NewsItemUIModel
import com.google.gson.annotations.SerializedName

data class NewsItem(
    @SerializedName("source") val source: Source?,
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("urlToImage") val urlToImage: String?,
    @SerializedName("publishedAt") val publishedAt: String?,
    @SerializedName("content") val content: String?
)

fun NewsItem.toUiModel(): NewsItemUIModel {
    return NewsItemUIModel(
        website = source?.name.orEmpty(),
        title = this.title.orEmpty(),
        imageUrl = this.urlToImage.orEmpty(),
        content = this.content.orEmpty(),
        publishedAt = this.publishedAt.orEmpty()
    )
}

data class Source(

    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)