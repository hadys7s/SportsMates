package com.example.sportsmates.di


import com.example.sportsmates.chatbot.ChatBotViewModel
import com.example.sportsmates.chatbot.endpoint.NutroEndPoint
import com.example.sportsmates.chatbot.repository.NutroRepository
import com.example.sportsmates.home.news.data.endpoint.NewsEndpoint
import com.example.sportsmates.home.news.data.repository.NewsRepository
import com.example.sportsmates.home.news.presentation.viewmodel.NewsViewModel
import com.example.sportsmates.networking.NewsApiHelper
import com.example.sportsmates.networking.NutroApiHelper
import com.example.sportsmates.networking.RetrofitBuilder
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val NutroModule = module {
    viewModel { ChatBotViewModel(get()) }
    factory { NutroRepository(get()) }
    factory {
        val api = RetrofitBuilder.nutroApiService
        NutroApiHelper(api)
    }
    single { get<Retrofit>().create(NutroEndPoint::class.java) }
}