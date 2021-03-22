package com.example.sportsmates

import android.app.Application
import com.example.sportsmates.di.SignUpModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class SportsMatesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SportsMatesApp)
            modules(listOf(SignUpModule))
        }
    }
}