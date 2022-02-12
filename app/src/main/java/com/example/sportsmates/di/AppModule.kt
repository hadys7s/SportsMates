package com.example.sportsmates.di

import com.example.sportsmates.UserPreferences
import com.example.sportsmates.auth.data.datasources.UserInfoDataSource
import com.example.sportsmates.auth.data.repo.UserRepository1
import com.example.sportsmates.auth.data.repo.UserRepositoryImp
import com.example.sportsmates.auth.domain.datainterfaces.UserRepository
import com.example.sportsmates.auth.domain.usecase.UserUseCase
import com.example.sportsmates.auth.presentation.signIn.SignInViewModel
import com.example.sportsmates.auth.presentation.signUp.viewmodel.SignUpViewModel
import com.example.sportsmates.booking.BookingViewModel
import com.example.sportsmates.chat.ChatViewModel
import com.example.sportsmates.coach.data.CoachesRepositoryImpl
import com.example.sportsmates.coach.domain.CoachUseCase
import com.example.sportsmates.coach.domain.CoachesRepository
import com.example.sportsmates.coach.presentation.CoachViewModel
import com.example.sportsmates.discover.ContactsViewModel
import com.example.sportsmates.editProfile.EditProfileViewModel
import com.example.sportsmates.home.data.repositories.EventsRepositoryImpl
import com.example.sportsmates.home.domain.datainterfaces.EventsRepository
import com.example.sportsmates.home.domain.usecases.EventsUseCase
import com.example.sportsmates.home.presentation.viewmodel.EventsViewModel
import com.example.sportsmates.place.data.PlacesRepositoryImpl
import com.example.sportsmates.place.domain.PlacesRepository
import com.example.sportsmates.place.domain.PlacesUseCase
import com.example.sportsmates.place.presentation.PLaceViewModel
import com.example.sportsmates.profile.ProfileViewModel
import com.example.sportsmates.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SignUpModule = module {
    factory { UserRepository1(get(), get()) }
    factory { UserPreferences(androidContext()) }
    factory { UserInfoDataSource(get()) }
    single<UserRepository> { UserRepositoryImp(get(), get()) }
    single<CoachesRepository> { CoachesRepositoryImpl() }
    factory { UserUseCase(get()) }
    factory { CoachUseCase(get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { EditProfileViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { CoachViewModel(get()) }
    factory<PlacesRepository> { PlacesRepositoryImpl() }
    single { PlacesUseCase(get(), get()) }
    viewModel { PLaceViewModel(get()) }
    viewModel { ContactsViewModel() }
    factory<EventsRepository> { EventsRepositoryImpl() }
    single { EventsUseCase(get(), get()) }
    viewModel { EventsViewModel(get()) }
    viewModel { ChatViewModel(get()) }
    viewModel { BookingViewModel(get()) }
    single { FirebaseAuth.getInstance() }
}
