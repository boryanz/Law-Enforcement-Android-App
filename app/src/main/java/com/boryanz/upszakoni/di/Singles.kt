package com.boryanz.upszakoni.di

import android.content.Context
import android.content.SharedPreferences
import com.boryanz.upszakoni.analytics.AnalyticsLogger
import com.boryanz.upszakoni.analytics.FirebaseAnalyticsManager
import com.boryanz.upszakoni.data.local.database.BonusSalaryDao
import com.boryanz.upszakoni.data.local.database.DocumentsHistoryDao
import com.boryanz.upszakoni.data.local.database.OwnedItemsDao
import com.boryanz.upszakoni.data.local.database.UpsDatabase
import com.boryanz.upszakoni.data.local.sharedprefs.PrefsLocalStorage
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import com.boryanz.upszakoni.data.remote.interceptors.ErrorInterceptor
import com.boryanz.upszakoni.data.remote.service.LawApiService
import com.boryanz.upszakoni.data.remote.service.OffensesApiService
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepositoryImpl
import com.boryanz.upszakoni.domain.laws.LawsProvider
import com.boryanz.upszakoni.domain.laws.LawsRepository
import com.boryanz.upszakoni.domain.offenses.OffensesProvider
import com.boryanz.upszakoni.domain.offenses.OffensesRepository
import com.boryanz.upszakoni.domain.remoteconfig.FirebaseRemoteConfig
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfigRepository
import com.google.firebase.analytics.FirebaseAnalytics
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 30L
fun Module.singles() {
  single {
    OkHttpClient.Builder()
      .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
      .readTimeout(TIMEOUT, TimeUnit.SECONDS)
      .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
      .addInterceptor(ErrorInterceptor())
      .build()
  }

  single {
    Retrofit.Builder()
      .baseUrl("http://10.0.2.2:8080/") //for testing purposes
      .client(get())
      .addConverterFactory(JacksonConverterFactory.create())
      .build()
  }
  single<LawApiService> { get<Retrofit>().create(LawApiService::class.java) }
  single<OffensesApiService> { get<Retrofit>().create(OffensesApiService::class.java) }

  single<SharedPreferences> {
    androidContext().getSharedPreferences("Ups shared prefs", Context.MODE_PRIVATE)
  }
  single<SharedPrefsManager> { PrefsLocalStorage(get()) }
  single<UpsDatabase> { UpsDatabase.getInstance(androidContext()) }
  single<BonusSalaryDao> { get<UpsDatabase>().bonusSalaryDao() }
  single<DocumentsHistoryDao> { get<UpsDatabase>().documentsDao() }
  single { get<DocumentsHistoryDao>() as com.boryanz.upszakoni.data.local.database.BaseDocumentsDao<com.boryanz.upszakoni.ui.screens.ai.history.GeneratedDocument> }
  single<OwnedItemsDao> { get<UpsDatabase>().ownedItemsDao() }
  single<BonusSalaryRepository> { BonusSalaryRepositoryImpl(get()) }
  single<FirebaseRemoteConfig> { RemoteConfigRepository() }
  single<FirebaseAnalytics> { FirebaseAnalytics.getInstance(androidContext()) }
  single<AnalyticsLogger> { FirebaseAnalyticsManager(get()) }
  single<OffensesProvider> { OffensesRepository(get()) }
  single<LawsProvider> { LawsRepository(get()) }
}