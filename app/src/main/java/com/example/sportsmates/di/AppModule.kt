package com.example.sportsmates.di

import com.example.sportsmates.UserPreferences
import com.example.sportsmates.booking.BookingViewModel
import com.example.sportsmates.chat.ChatViewModel
import com.example.sportsmates.coach.CoachViewModel
import com.example.sportsmates.discover.ContactsViewModel
import com.example.sportsmates.editProfile.EditProfileViewModel
import com.example.sportsmates.home.data.repositories.EventsRepositoryImpl
import com.example.sportsmates.home.domain.datainterfaces.EventsRepository
import com.example.sportsmates.home.domain.usecases.EventsUseCase
import com.example.sportsmates.home.presentation.viewmodel.EventsViewModel
import com.example.sportsmates.login.SignInViewModel
import com.example.sportsmates.place.data.PlacesRepositoryImpl
import com.example.sportsmates.place.domain.PlacesRepository
import com.example.sportsmates.place.domain.PlacesUseCase
import com.example.sportsmates.place.presentation.PLaceViewModel
import com.example.sportsmates.profile.ProfileViewModel
import com.example.sportsmates.signUp.data.repo.UserRepository
import com.example.sportsmates.signUp.viewmodel.SignUpViewModel
import com.example.sportsmates.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SignUpModule = module {
    factory { UserRepository(get(), get()) }
    factory { UserPreferences(androidContext()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { EditProfileViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { CoachViewModel() }
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
