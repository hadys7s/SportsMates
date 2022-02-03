package com.example.sportsmates.home.domain.usecases

import com.example.sportsmates.home.domain.datainterfaces.NewsRepository
import com.example.sportsmates.home.domain.entities.NewsItem
import com.example.sportsmates.auth.data.repo.UserRepository

class NewsUseCase(
    private val newsRepository: NewsRepository,
    private val userRepository: UserRepository
) {
    suspend fun getRecommendedNewsBasedOnUserSports(
    ): List<NewsItem> {
        val sportsList = userRepository.getUserSportsList()
        return when (sportsList?.size) {
            3 -> newsRepository.getNews(
                sportsList[0],
                sportsList[1],
                sportsList[2]
            )
            2 ->
                newsRepository.getNews(
                    sportsList[0],
                    sportsList[1]
                )

            1 -> newsRepository.getNews(
                sportsList[0]
            )
            else -> return emptyList()
        }
    }

    suspend fun getSportsTrendingNews(
    ) = newsRepository.getTrendingNews()

}