package com.example.sportsmates.home.domain.datainterfaces

import com.example.sportsmates.home.domain.entities.NewsItem

interface NewsRepository {
    suspend fun getNews(
        mainSport: String?,
        optionalSport1: String? = mainSport,
        optionalSport2: String? = mainSport
    ): List<NewsItem>
    suspend fun getTrendingNews(): List<NewsItem>

}