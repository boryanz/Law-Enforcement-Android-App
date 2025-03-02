package com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.daily

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel


sealed interface NewOvertimeInputAction {
    data class OnCreate(
        val monthName: String,
        val monthId: Int,
        val dayNumber: Int,
        val totalOvertime: String,
        val isSickDay: Boolean,
        val isPaidLeave: Boolean
    ) : NewOvertimeInputAction

    data class OvertimeValueEntered(val value: String) : NewOvertimeInputAction
    data class PaidLeaveClicked(val value: Boolean) : NewOvertimeInputAction
    data class SickLeaveClicked(val value: Boolean) : NewOvertimeInputAction
    data object SaveClicked : NewOvertimeInputAction
}


@Composable
fun NewOvertimeInputScreen(
    monthId: Int,
    monthName: String,
    dayNumber: Int,
    isSickDay: Boolean,
    isPaidLeave: Boolean,
    totalOvertime: String,
    onBackClicked: () -> Unit,
    onSaveClicked: () -> Unit,
) {
    val viewModel = koinViewModel<NewOverTimeInputViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onUiEvent(
            NewOvertimeInputAction.OnCreate(
                monthName = monthName,
                totalOvertime = totalOvertime,
                isSickDay = isSickDay,
                isPaidLeave = isPaidLeave,
                monthId = monthId,
                dayNumber = dayNumber
            )
        )
    }

    NewOvertimeInputContent(
        uiState = uiState,
        month = monthName,
        dayInMonth = dayNumber,
        onOvertimeHoursChanged = {
            viewModel.onUiEvent(
                NewOvertimeInputAction.OvertimeValueEntered(it)
            )
        },
        onBackClicked = onBackClicked,
        onSaveClicked = {
            viewModel.onUiEvent(NewOvertimeInputAction.SaveClicked)
            onSaveClicked()
        },
        onSickDayClicked = {
            viewModel.onUiEvent(NewOvertimeInputAction.SickLeaveClicked(it))
        },
        onPaidLeaveClicked = {
            viewModel.onUiEvent(NewOvertimeInputAction.PaidLeaveClicked(it))
        }
    )
}