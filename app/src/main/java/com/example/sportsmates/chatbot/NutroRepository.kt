package com.example.sportsmates.chatbot.repository

import com.example.sportsmates.networking.NutroApiHelper

class NutroRepository(val apiHelper: NutroApiHelper) {
    suspend  fun getNutroInfo(food: String) = apiHelper.
    getNutroInfo(food)

}