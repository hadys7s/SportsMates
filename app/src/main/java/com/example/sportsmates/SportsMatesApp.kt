package com.example.sportsmates

import android.app.Application
import com.example.sportsmates.di.NewsModule
import com.example.sportsmates.di.SignUpModule
import io.kommunicate.Kommunicate
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class SportsMatesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SportsMatesApp)
            modules(listOf(SignUpModule, NewsModule))
        }
        Kommunicate.init(this, APP_ID);

    }

    companion object {
        const val APP_ID = "2dfecefa88cf709d50e8e1341efb528d1"
    }
}