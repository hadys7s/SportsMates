package com.example.sportsmates.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.sportsmates.home.domain.entities.NewsItem
import com.example.sportsmates.home.domain.usecases.NewsUseCase
import com.example.sportsmates.networking.Resource
import kotlinx.coroutines.Dispatchers

class NewsViewModel(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    fun getRecommendedNews(): LiveData<Resource<List<NewsItem>>> = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(
                Resource.Success(
                    data = newsUseCase.getRecommendedNewsBasedOnUserSports()
                )
            )
        } catch (exception: Exception) {
            emit(Resource.Error(exception))
        }
    }


    fun getTrendingNews(): LiveData<Resource<List<NewsItem>>> = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(data = newsUseCase.getSportsTrendingNews()))
        } catch (exception: Exception) {
            emit(Resource.Error(exception))
        }
    }

}
