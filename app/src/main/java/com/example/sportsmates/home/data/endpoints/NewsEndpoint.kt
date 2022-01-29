package com.example.sportsmates.home.data.endpoints

import com.example.sportsmates.home.data.datamodels.NewsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsEndpoint {

    @GET("everything")
    suspend fun getRecommendedNews(
        @Query("q") sports: String?
    ): NewsListResponse

    @GET("top-headlines?category=sports&country=us")
    suspend fun getTrendingNews(): NewsListResponse

}
