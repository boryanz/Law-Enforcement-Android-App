package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.ui.components.Loader
import com.boryanz.upszakoni.utils.collectEvents
import org.koin.androidx.compose.koinViewModel


sealed interface BonusSalaryDashboardUiEvent {
  data object FetchMonthlyStats : BonusSalaryDashboardUiEvent
  data object DeleteAllActionButtonClicked : BonusSalaryDashboardUiEvent
  data object DeleteButtonClicked : BonusSalaryDashboardUiEvent
  data object UndoDeleteAllActionClicked : BonusSalaryDashboardUiEvent
}


@Composable
fun BonusSalaryDashboardScreen(
  onEditClicked: () -> Unit,
  onBackClicked: () -> Unit,
  onMonthClicked: (String) -> Unit,
  onNonWorkingDaysClicked: (String) -> Unit,
) {

  val viewModel = koinViewModel<BonusSalaryDashboardViewModel>()
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  LifecycleEventEffect(event = ON_CREATE) {
    viewModel.onUiEvent(BonusSalaryDashboardUiEvent.FetchMonthlyStats)
  }

  viewModel.event.collectEvents { event ->
    when (event) {
      BonusSalaryDashboardEvent.AllDataDeleted -> onBackClicked()
    }
  }

  if (uiState.isLoading) Loader()

  BonusSalaryDashboardContent(
    uiState = uiState,
    onUiEvent = viewModel::onUiEvent,
    onMonthClicked = onMonthClicked,
    onBackClicked = onBackClicked,
    onEditClicked = onEditClicked,
    onNonWorkingDaysClicked = { onNonWorkingDaysClicked(it) }
  )
}