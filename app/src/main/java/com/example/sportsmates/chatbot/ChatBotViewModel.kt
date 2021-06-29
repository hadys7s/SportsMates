package com.example.sportsmates.chatbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.sportsmates.chatbot.model.toUiModel
import com.example.sportsmates.chatbot.repository.NutroRepository

import com.example.sportsmates.networking.Resource
import kotlinx.coroutines.Dispatchers

class ChatBotViewModel(private val nutroRepository: NutroRepository) : ViewModel() {

    fun getNutroInfo(food: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = nutroRepository.getNutroInfo(food).foods.map { it.toUiModel() }
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}