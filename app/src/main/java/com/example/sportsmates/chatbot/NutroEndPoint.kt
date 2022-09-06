package com.example.sportsmates.chatbot.endpoint

import com.example.sportsmates.chatbot.model.Nutro
import retrofit2.http.Body
import retrofit2.http.POST

interface NutroEndPoint {
    @POST("natural/nutrients")
    suspend fun getNutroInfo(
        @Body fields: HashMap<String, String>
    ): Nutro
}
