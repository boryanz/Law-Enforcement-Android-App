package com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.boryanz.upszakoni.ui.navigation.navigationwrapper.NavigationWrapperImpl
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.AbsenceDaysValueChanged
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.OvertimeHoursValueChanged
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.SickDaysValueChanged
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

sealed interface OverTimeInputUiEvent {
    data class AbsenceDaysValueChanged(val value: String) : OverTimeInputUiEvent
    data class OvertimeHoursValueChanged(val value: String) : OverTimeInputUiEvent
    data class SickDaysValueChanged(val value: String) : OverTimeInputUiEvent
    data class SaveClicked(val month: String) : OverTimeInputUiEvent
    data class FetchMonthlyStats(val month: String): OverTimeInputUiEvent
}

@Composable
fun BonusSalaryOverTimeInputScreen(
    navHostController: NavHostController,
    month: String,
) {
    val navigator = remember { NavigationWrapperImpl(navHostController) }
    val viewModel = koinViewModel<OverTimeInputViewModel>(parameters = { parametersOf(navigator) })
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onUiEvent(OverTimeInputUiEvent.FetchMonthlyStats(month))
    }

    OverTimeInputContent(
        uiState = uiState,
        month = month,
        onBackClicked = { navHostController.navigateUp() },
        onAbsenceDaysValueChanged = { viewModel.onUiEvent(AbsenceDaysValueChanged(it)) },
        onOvertimeHoursValueChanged = { viewModel.onUiEvent(OvertimeHoursValueChanged(it)) },
        onSickDaysValueChanged = { viewModel.onUiEvent(SickDaysValueChanged(it)) },
        onSaveClicked = { viewModel.onUiEvent(OverTimeInputUiEvent.SaveClicked(month)) }
    )
}