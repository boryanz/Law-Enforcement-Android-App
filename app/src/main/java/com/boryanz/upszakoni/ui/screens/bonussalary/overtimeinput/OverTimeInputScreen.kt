package com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun BonusSalaryOverTimeInputScreen(
    navHostController: NavHostController,
    month: String,
) {
    //viewmodel here
    OverTimeInputContent(
        month = month,
        onBackClicked = { navHostController.navigateUp() },
        onAbsenceDaysValueChanged = {},
        onOvertimeHoursValueChanged = {},
        onSickDaysValueChanged = {},
        onSaveClicked = {}
    )
}