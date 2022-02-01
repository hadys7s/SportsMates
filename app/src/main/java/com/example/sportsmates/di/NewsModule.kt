package com.example.sportsmates.di


import com.example.sportsmates.home.data.endpoints.NewsEndpoint
import com.example.sportsmates.home.data.repositories.NewsRepositoryImpl
import com.example.sportsmates.home.domain.datainterfaces.NewsRepository
import com.example.sportsmates.home.domain.usecases.NewsUseCase
import com.example.sportsmates.home.presentation.viewmodel.NewsViewModel
import com.example.sportsmates.networking.NewsApiHelper
import com.example.sportsmates.networking.RetrofitBuilder
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val NewsModule = module {
    factory { NewsRepositoryImpl(get()) }
    factory<NewsRepository> { NewsRepositoryImpl(get()) }
    single { NewsUseCase(get(), get()) }
    factory {
        val api = RetrofitBuilder.newsApiService
        NewsApiHelper(api)
    }
    single { get<Retrofit>().create(NewsEndpoint::class.java) }
    viewModel { NewsViewModel(get()) }
}
