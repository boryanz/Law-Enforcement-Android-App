package com.boryanz.upszakoni.di

import android.content.Context
import android.content.SharedPreferences
import com.boryanz.upszakoni.data.local.database.BonusSalaryDao
import com.boryanz.upszakoni.data.local.database.UpsDatabase
import com.boryanz.upszakoni.data.local.sharedprefs.PrefsLocalStorage
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import com.boryanz.upszakoni.domain.DaysInMonthDataGenerator
import com.boryanz.upszakoni.domain.GenerateDaysInMonthsUseCase
import com.boryanz.upszakoni.domain.GetLawsUseCase
import com.boryanz.upszakoni.domain.LawsUseCase
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepositoryImpl
import com.boryanz.upszakoni.domain.remoteconfig.FirebaseRemoteConfig
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfigRepository
import com.boryanz.upszakoni.ui.navigation.navgraph.overtimetracking.OvertimeTrackNavigationGraphViewModel
import com.boryanz.upszakoni.ui.screens.archivedlaws.ArchivedLawsViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.monthly.OvertimeMonthlyCalendarViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.MigrationProposalViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.daily.NewOverTimeInputViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersViewModel
import com.boryanz.upszakoni.ui.screens.informations.InformationScreenViewModel
import com.boryanz.upszakoni.ui.screens.laws.LawsViewModel
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
  factory<DaysInMonthDataGenerator> { GenerateDaysInMonthsUseCase() }
  single<BonusSalaryRepository> { BonusSalaryRepositoryImpl(get()) }
  single<FirebaseRemoteConfig> { RemoteConfigRepository() }
  factory<LawsUseCase> { GetLawsUseCase(androidContext()) }
  viewModel { ArchivedLawsViewModel(get(), get()) }
  viewModel { BonusSalaryParametersViewModel(get()) }
  viewModel { MigrationProposalViewModel(get(), get(), get()) }
  viewModel { OverTimeInputViewModel(get()) }
  viewModel { BonusSalaryDashboardViewModel(get(), get(), get()) }
  viewModel { LawsViewModel(get(), get(), get()) }
  viewModel { InformationScreenViewModel(get()) }
  viewModel { OvertimeTrackNavigationGraphViewModel(get()) }
  viewModel { OvertimeMonthlyCalendarViewModel(get()) }
  viewModel { NewOverTimeInputViewModel(get()) }
}