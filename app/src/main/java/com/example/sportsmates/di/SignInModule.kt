package com.example.sportsmates.di

import com.example.sportsmates.signUp.data.Repo.UserRepository
import com.example.sportsmates.signUp.viewmodel.SignUpViewModel
import com.example.sportsmates.login.SignInViewModel
import com.example.sportsmates.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SignUpModule = module {
    factory { UserRepository(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { ProfileViewModel(get()) }

    single { FirebaseAuth.getInstance() }

}

