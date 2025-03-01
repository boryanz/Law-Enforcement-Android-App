package com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput

import androidx.compose.runtime.Composable

@Composable
fun NewOvertimeInputScreen(
    monthId: String,
    dayOfTheMonthId: String,
    onBackClicked: () -> Unit,
    onSaveClicked: () -> Unit,
) {
    NewOvertimeInputContent(
        uiState = UiState(),
        month = "January",
        dayInMonth = "3",
        onOvertimeHoursChanged = {},
        onBackClicked = {},
        onSaveClicked = {},
        onSickDayClicked = {},
        onPaidLeaveClicked = {}
    )
}