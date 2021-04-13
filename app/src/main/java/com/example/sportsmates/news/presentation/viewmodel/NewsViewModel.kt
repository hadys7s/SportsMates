package com.example.sportsmates.news.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.sportsmates.networking.Resource
import com.example.sportsmates.news.data.repository.NewsRepository
import com.example.sportsmates.news.toUiModel
import kotlinx.coroutines.Dispatchers

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    fun getRecommendedNews() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = newsRepository.getRecommendedNews().articles.map { it.toUiModel() }))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getTrendingNews() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = newsRepository.getTrendingNews().articles.map { it.toUiModel() }))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}