package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.monthly

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun OvertimeMonthlyCalendarScreen(
    navHostController: NavHostController,
    monthId: String,
    onDayInMonthClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    OvertimeMonthlyCalendarContent(
        month = monthId,
        onBackClicked = onBackClicked,
        onDayInMonthClicked = onDayInMonthClicked
    )
}