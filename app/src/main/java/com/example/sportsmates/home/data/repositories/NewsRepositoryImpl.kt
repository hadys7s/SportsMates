package com.example.sportsmates.home.data.repositories

import com.example.sportsmates.home.data.datamodels.toDomainModel
import com.example.sportsmates.home.domain.datainterfaces.NewsRepository
import com.example.sportsmates.networking.NewsApiHelper


class NewsRepositoryImpl(private val apiHelper: NewsApiHelper) : NewsRepository {

    override suspend fun getNews(
        mainSport: String?,
        optionalSport1: String?,
        optionalSport2: String?
    ) =
        apiHelper.getNews("$mainSport OR $optionalSport1 OR $optionalSport2").articles.map { it.toDomainModel() }

    override suspend fun getTrendingNews() = apiHelper.getTrendingNews().articles.map { it.toDomainModel() }


}
