package com.example.sportsmates.di


import com.example.sportsmates.home.news.data.endpoint.NewsEndpoint
import com.example.sportsmates.home.news.data.repository.NewsRepository
import com.example.sportsmates.home.news.presentation.viewmodel.NewsViewModel
import com.example.sportsmates.networking.ApiHelper
import com.example.sportsmates.networking.RetrofitBuilder
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val NewsModule = module {
    viewModel { NewsViewModel(get(),get()) }
    factory { NewsRepository(get()) }
    factory {
        val api = RetrofitBuilder.apiService
        ApiHelper(api)
    }
    single { get<Retrofit>().create(NewsEndpoint::class.java) }
}


