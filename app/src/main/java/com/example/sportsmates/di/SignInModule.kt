package com.example.sportsmates.di

import com.example.sportsmates.SignUp.data.Repo.UserRepository
import com.example.sportsmates.SignUp.viewmodel.SignUpViewModel
import com.example.sportsmates.login.SignInViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SignUpModule = module {
    factory { UserRepository(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }

    single { FirebaseAuth.getInstance() }

}

