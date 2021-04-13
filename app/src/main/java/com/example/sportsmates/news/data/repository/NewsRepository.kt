package com.example.sportsmates.news.data.repository

import com.example.sportsmates.networking.ApiHelper
import com.example.sportsmates.networking.Resource
import com.example.sportsmates.news.data.endpoint.NewsEndpoint
import com.example.sportsmates.utils.Constants
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Request;


class NewsRepository(private val apiHelper: ApiHelper) {

    suspend fun getRecommendedNews() = apiHelper.getNews("football")
    suspend fun getTrendingNews() = apiHelper.getTrendingNews()


}

