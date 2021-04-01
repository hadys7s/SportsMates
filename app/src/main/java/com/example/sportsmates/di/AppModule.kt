package com.example.sportsmates.di

import android.app.Application
import com.example.sportsmates.coach.CoachViewModel
import com.example.sportsmates.signUp.data.Repo.UserRepository
import com.example.sportsmates.signUp.viewmodel.SignUpViewModel
import com.example.sportsmates.login.SignInViewModel
import com.example.sportsmates.profile.ProfileViewModel
import com.example.sportsmates.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SignUpModule = module {
    factory { UserRepository(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { CoachViewModel() }


    single { FirebaseAuth.getInstance() }

}

