package com.example.katasampleapp

import android.app.Application
import com.example.katasampleapp.di.appModule
import org.koin.core.context.startKoin


class KataSampleApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            modules(appModule)
        }
    }
}