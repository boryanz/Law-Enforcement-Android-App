package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.monthly

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.ui.components.Loader
import org.koin.androidx.compose.koinViewModel

@Composable
fun OvertimeMonthlyCalendarScreen(
  monthName: String,
  onDayInMonthClicked: (DayInMonth) -> Unit,
  onBackClicked: () -> Unit
) {
  val viewModel = koinViewModel<OvertimeMonthlyCalendarViewModel>()
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
    viewModel.getDailyStats(monthName)
  }

  if (uiState.isLoading) {
    Loader()
  } else {
    OvertimeMonthlyCalendarContent(
      uiState = uiState,
      month = monthName,
      onBackClicked = onBackClicked,
      onDayInMonthClicked = { onDayInMonthClicked(it) }
    )
  }
}