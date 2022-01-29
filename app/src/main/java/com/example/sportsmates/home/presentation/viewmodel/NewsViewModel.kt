package com.example.sportsmates.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.ext.Events
import com.example.sportsmates.home.domain.entities.NewsItem
import com.example.sportsmates.home.domain.usecases.NewsUseCase
import com.example.sportsmates.networking.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class NewsViewModel(
    private val newsUseCase: NewsUseCase
) : ViewModel() {
    private val _recommendedNewsState = MutableStateFlow<Resource<List<NewsItem>>>(Resource.Loading)
    val recommendedNewsState get() = _recommendedNewsState.asStateFlow()
    private val _trendingNewsState = MutableStateFlow<Resource<List<NewsItem>>>(Resource.Loading)
    val trendingNewsState get() = _trendingNewsState.asStateFlow()

    fun onViewCreated(){
        getRecommendedNews()
        getTrendingNews()
    }
    fun getRecommendedNews() =
        viewModelScope.launch() {
            try {
                _recommendedNewsState.emit(Resource.Success(data = newsUseCase.getRecommendedNewsBasedOnUserSports()))
            } catch (exception: Exception) {
                _recommendedNewsState.emit(Resource.Error(exception))
            }

        }


    fun getTrendingNews() =
        viewModelScope.launch {
            try {
                _trendingNewsState.emit(Resource.Success(data = newsUseCase.getSportsTrendingNews()))
            } catch (exception: Exception) {
                _trendingNewsState.emit(Resource.Error(exception))
            }
        }

    fun handleErrors() = viewModelScope.launch {
        Events.event.collectLatest {

        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("RAfaa", "ss")
    }

}
