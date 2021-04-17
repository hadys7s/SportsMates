package com.example.sportsmates.home.news.data.repository

import com.example.sportsmates.networking.ApiHelper


class NewsRepository(private val apiHelper: ApiHelper) {

    suspend fun getRecommendedNews(
        listOfSports: List<String>?
    ) = when {
        listOfSports?.size!! == 3 -> apiHelper.getNews(
            listOfSports[0],
            listOfSports[1],
            listOfSports[2]
        )
        listOfSports?.size!! == 2 -> apiHelper.getNews(
            listOfSports[0],
            listOfSports[1]
        )
        else -> apiHelper.getNews(
            listOfSports[0]
        )
    }

    suspend fun getTrendingNews() = apiHelper.getTrendingNews()

}
