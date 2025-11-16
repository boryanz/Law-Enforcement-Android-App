package com.boryanz.upszakoni

import android.app.Application
import com.boryanz.upszakoni.analytics.FirebaseAnalyticsManager
import com.boryanz.upszakoni.di.appModule
import com.boryanz.upszakoni.domain.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.FirebaseApp
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class UpsApp : Application() {

  private val remoteConfigRepository: FirebaseRemoteConfig by inject()

  override fun onCreate() {
    super.onCreate()
    FirebaseApp.initializeApp(this)
    initializeKoin()
    initializeAnalytics()
    initializeRemoteConfig()
  }

  private fun initializeAnalytics() {
    FirebaseAnalyticsManager.init(this)
  }

  private fun initializeRemoteConfig() {
    remoteConfigRepository.init()
  }

  private fun initializeKoin() {
    startKoin {
      androidContext(this@UpsApp)
      modules(appModule)
    }
  }
}