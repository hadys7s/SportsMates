package com.example.sportsmates.news.data.endpoint

import com.example.sportsmates.news.data.model.NewsListResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsEndpoint {
    @GET("everything")
    suspend fun getRecommendedNews(
        @Query("q") sports: String?
    ): NewsListResponse

    @GET("top-headlines?category=sports&country=us")
    suspend fun getTrendingNews(): NewsListResponse

}
