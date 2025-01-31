package com.boryanz.upszakoni.di

import com.boryanz.upszakoni.domain.GetLawsUseCase
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepositoryImpl
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfigRepository
import com.boryanz.upszakoni.ui.navigation.navigationwrapper.NavigationWrapper
import com.boryanz.upszakoni.ui.screens.bonussalary.BonusSalaryViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersViewModel
import com.boryanz.upszakoni.ui.screens.laws.LawsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // add your dependencies here
    //  single { get<KataRestClient>().createService(com.boryanz.upszakoni.data.remote.restapi.KataRestApi::class.java) }

    single<BonusSalaryRepository> { BonusSalaryRepositoryImpl(androidContext()) }
    single { RemoteConfigRepository() }
    factory { GetLawsUseCase() }
    viewModel { (navigator: NavigationWrapper) -> BonusSalaryParametersViewModel(get(), navigator) }
    viewModel { BonusSalaryViewModel(get()) }
    viewModel { (navigator: NavigationWrapper) -> OverTimeInputViewModel(get(), navigator) }
    viewModel { BonusSalaryDashboardViewModel(get()) }
    viewModel { LawsViewModel(get(), get()) }
}