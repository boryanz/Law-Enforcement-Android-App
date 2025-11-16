package com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.AbsenceDaysValueChanged
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.OvertimeHoursValueChanged
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.SickDaysValueChanged
import com.boryanz.upszakoni.utils.collectEvents
import org.koin.androidx.compose.koinViewModel

sealed interface OverTimeInputUiEvent {
  data class AbsenceDaysValueChanged(val value: String) : OverTimeInputUiEvent
  data class OvertimeHoursValueChanged(val value: String) : OverTimeInputUiEvent
  data class SickDaysValueChanged(val value: String) : OverTimeInputUiEvent
  data class SaveClicked(val month: String) : OverTimeInputUiEvent
  data class FetchMonthlyStats(val month: String) : OverTimeInputUiEvent
}

@Composable
fun BonusSalaryOverTimeInputScreen(
  onBackClicked: () -> Unit,
  month: String,
) {
  val viewModel = koinViewModel<OverTimeInputViewModel>()
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  viewModel.event.collectEvents {
    when (it) {
      OvertimeInputEvent.StatsSaved -> onBackClicked()
    }
  }

  LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
    viewModel.onUiEvent(OverTimeInputUiEvent.FetchMonthlyStats(month))
  }

  OverTimeInputContent(
    uiState = uiState,
    month = month,
    onBackClicked = onBackClicked,
    onAbsenceDaysValueChanged = { viewModel.onUiEvent(AbsenceDaysValueChanged(it)) },
    onOvertimeHoursValueChanged = { viewModel.onUiEvent(OvertimeHoursValueChanged(it)) },
    onSickDaysValueChanged = { viewModel.onUiEvent(SickDaysValueChanged(it)) },
    onSaveClicked = { viewModel.onUiEvent(OverTimeInputUiEvent.SaveClicked(month)) }
  )
}