package com.boryanz.upszakoni.di

import android.content.Context
import android.content.SharedPreferences
import com.boryanz.upszakoni.analytics.AnalyticsLogger
import com.boryanz.upszakoni.analytics.FirebaseAnalyticsManager
import com.boryanz.upszakoni.data.local.database.BonusSalaryDao
import com.boryanz.upszakoni.data.local.database.OwnedItemsDao
import com.boryanz.upszakoni.data.local.database.UpsDatabase
import com.boryanz.upszakoni.data.local.sharedprefs.PrefsLocalStorage
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import com.boryanz.upszakoni.domain.DaysInMonthDataGenerator
import com.boryanz.upszakoni.domain.GenerateDaysInMonthsUseCase
import com.boryanz.upszakoni.domain.GetLawsUseCase
import com.boryanz.upszakoni.domain.LawsUseCase
import com.boryanz.upszakoni.domain.ai.AiGenerationChecker
import com.boryanz.upszakoni.domain.ai.AiGenerator
import com.boryanz.upszakoni.domain.ai.CheckAiGenerationsUseCase
import com.boryanz.upszakoni.domain.ai.GenerateAiDocument
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepositoryImpl
import com.boryanz.upszakoni.domain.owneditem.AddOwnedItemUseCase
import com.boryanz.upszakoni.domain.owneditem.AddOwnedItemUseCaseImpl
import com.boryanz.upszakoni.domain.owneditem.DeleteOwnedItemUseCase
import com.boryanz.upszakoni.domain.owneditem.DeleteOwnedItemUseCaseImpl
import com.boryanz.upszakoni.domain.owneditem.GetOwnedItemsUseCase
import com.boryanz.upszakoni.domain.owneditem.GetOwnedItemsUseCaseImpl
import com.boryanz.upszakoni.domain.remoteconfig.FirebaseRemoteConfig
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfigRepository
import com.boryanz.upszakoni.ui.navigation.navgraph.overtimetracking.OvertimeTrackNavigationGraphViewModel
import com.boryanz.upszakoni.ui.owneditem.addowneditem.OwnedItemViewModel
import com.boryanz.upszakoni.ui.owneditem.overview.OwnedItemsListViewModel
import com.boryanz.upszakoni.ui.screens.ai.addprompt.AddPromptViewModel
import com.boryanz.upszakoni.ui.screens.ai.document.DocumentScreenViewModel
import com.boryanz.upszakoni.ui.screens.archivedlaws.ArchivedLawsViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.monthly.OvertimeMonthlyCalendarViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.MigrationProposalViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.daily.NewOverTimeInputViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersViewModel
import com.boryanz.upszakoni.ui.screens.informations.InformationScreenViewModel
import com.boryanz.upszakoni.ui.screens.laws.LawsViewModel
import com.google.firebase.Firebase
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
  single<SharedPreferences> {
    androidContext().getSharedPreferences("Ups shared prefs", Context.MODE_PRIVATE)
  }
  single<SharedPrefsManager> { PrefsLocalStorage(get()) }
  single<UpsDatabase> { UpsDatabase.getInstance(androidContext()) }
  single<BonusSalaryDao> { get<UpsDatabase>().bonusSalaryDao() }
  single<OwnedItemsDao> { get<UpsDatabase>().ownedItemsDao() }
  factory<DaysInMonthDataGenerator> { GenerateDaysInMonthsUseCase() }
  single<BonusSalaryRepository> { BonusSalaryRepositoryImpl(get()) }
  single<FirebaseRemoteConfig> { RemoteConfigRepository() }
  single<FirebaseAnalytics> { FirebaseAnalytics.getInstance(androidContext()) }
  single<AnalyticsLogger> { FirebaseAnalyticsManager(get()) }
  factory<GenerativeModel> {
    Firebase.ai(backend = GenerativeBackend.googleAI()).generativeModel("gemini-2.5-flash")
  }
  factory<LawsUseCase> { GetLawsUseCase(androidContext()) }
  factory<AiGenerationChecker> { CheckAiGenerationsUseCase(get()) }
  factory<AiGenerator> { GenerateAiDocument(get()) }
  factory<GetOwnedItemsUseCase> { GetOwnedItemsUseCaseImpl(get()) }
  factory<DeleteOwnedItemUseCase> { DeleteOwnedItemUseCaseImpl(get()) }
  factory<AddOwnedItemUseCase> { AddOwnedItemUseCaseImpl(get()) }
  viewModel { DocumentScreenViewModel(get(), get()) }
  viewModel { ArchivedLawsViewModel(get(), get(), get<AnalyticsLogger>()) }
  viewModel { BonusSalaryParametersViewModel(get()) }
  viewModel { MigrationProposalViewModel(get(), get(), get()) }
  viewModel { OverTimeInputViewModel(get(), get<AnalyticsLogger>()) }
  viewModel { BonusSalaryDashboardViewModel(get(), get(), get<AnalyticsLogger>(), get()) }
  viewModel { LawsViewModel(get(), get(), get(), get<AnalyticsLogger>()) }
  viewModel { InformationScreenViewModel(get()) }
  viewModel { OvertimeTrackNavigationGraphViewModel(get()) }
  viewModel { OvertimeMonthlyCalendarViewModel(get()) }
  viewModel { NewOverTimeInputViewModel(get()) }
  viewModel { OwnedItemViewModel(get()) }
  viewModel { OwnedItemsListViewModel(get(), get()) }
  viewModel { AddPromptViewModel(get()) }
}