package com.example.sportsmates.home.news.presentation.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.sportsmates.home.news.data.model.toUiModel
import com.example.sportsmates.home.news.data.repository.NewsRepository
import com.example.sportsmates.home.news.presentation.model.NewsItemUIModel
import com.example.sportsmates.networking.Resource
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.signUp.data.repo.UserRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.asDeferred

class NewsViewModel(private val newsRepository: NewsRepository, userRepository: UserRepository) :
    ViewModel() {

    var listOfSports: List<String>? = listOf()

    init {
        userRepository.fetchUserData()
        userRepository.retrievePhoto()
    }


    fun getRecommendedNews(): LiveData<Resource<List<NewsItemUIModel>>> = liveData(Dispatchers.IO) {
        getUserSportsList()
        emit(Resource.Loading)
        try {
            emit(
                Resource.Success(data = newsRepository.getRecommendedNews(
                    listOfSports
                ).articles.map { it.toUiModel() })
            )
        } catch (exception: Exception) {
            emit(Resource.Error(exception))
        }
    }


    fun getTrendingNews(): LiveData<Resource<List<NewsItemUIModel>>> = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(data = newsRepository.getTrendingNews().articles.map { it.toUiModel() }))
        } catch (exception: Exception) {
            emit(Resource.Error(exception))
        }
    }

    private suspend fun getUserSportsList() {
        FirebaseDatabase.getInstance().getReference("Users")
            .child(Firebase.auth.currentUser.uid)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "getUser:Success")
                    val user: User? = task.result?.getValue(User::class.java)
                    listOfSports = user?.sportsList?.toMutableList()
                } else {
                    Log.d(ContentValues.TAG, "getUser:Failed", task.exception)
                }
            }.asDeferred().await()

    }
}
