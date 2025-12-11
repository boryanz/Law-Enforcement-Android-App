package com.boryanz.upszakoni.di

import com.boryanz.upszakoni.analytics.AnalyticsLogger
import com.boryanz.upszakoni.ui.navigation.navgraph.overtimetracking.OvertimeTrackNavigationGraphViewModel
import com.boryanz.upszakoni.ui.owneditem.addowneditem.OwnedItemViewModel
import com.boryanz.upszakoni.ui.owneditem.overview.OwnedItemsListViewModel
import com.boryanz.upszakoni.ui.screens.ai.addprompt.AddPromptViewModel
import com.boryanz.upszakoni.ui.screens.ai.document.DocumentScreenViewModel
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.monthly.OvertimeMonthlyCalendarViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.MigrationProposalViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.daily.NewOverTimeInputViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersViewModel
import com.boryanz.upszakoni.ui.screens.informations.InformationScreenViewModel
import com.boryanz.upszakoni.ui.screens.laws.LawsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module

fun Module.viewModels() {
  viewModel { DocumentScreenViewModel(get(), get()) }
  viewModel { BonusSalaryParametersViewModel(get()) }
  viewModel { MigrationProposalViewModel(get(), get(), get()) }
  viewModel { OverTimeInputViewModel(get(), get<AnalyticsLogger>()) }
  viewModel { BonusSalaryDashboardViewModel(get(), get(), get<AnalyticsLogger>(), get()) }
  viewModel { LawsViewModel(get(), get()) }
  viewModel { InformationScreenViewModel(get()) }
  viewModel { OvertimeTrackNavigationGraphViewModel(get()) }
  viewModel { OvertimeMonthlyCalendarViewModel(get()) }
  viewModel { NewOverTimeInputViewModel(get()) }
  viewModel { OwnedItemViewModel(get()) }
  viewModel { OwnedItemsListViewModel(get(), get()) }
  viewModel { AddPromptViewModel() }
  viewModel { DocumentHistoryViewModel(get()) }
}