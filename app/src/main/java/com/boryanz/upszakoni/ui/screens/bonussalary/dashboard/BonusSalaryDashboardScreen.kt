package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.ui.components.Loader
import org.koin.androidx.compose.koinViewModel

@Composable
fun BonusSalaryDashboardScreen(
  onEditClicked: () -> Unit,
  onBackClicked: () -> Unit,
  onMonthClicked: (String) -> Unit,
  onNonWorkingDaysClicked: (String) -> Unit,
) {

  val viewModel = koinViewModel<BonusSalaryDashboardViewModel>()
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
    viewModel.onUiEvent(BonusSalaryDashboardUiEvent.FetchMonthlyStats)
  }

  if (uiState.isLoading) Loader()

  BonusSalaryDashboardContent(
    uiState = uiState,
    onMonthClicked = onMonthClicked,
    onBackClicked = onBackClicked,
    onDeleteAllClicked = {
      viewModel.onUiEvent(BonusSalaryDashboardUiEvent.DeleteAll)
      onBackClicked()
    },
    onEditClicked = onEditClicked,
    onNonWorkingDaysClicked = { onNonWorkingDaysClicked(it) }
  )
}