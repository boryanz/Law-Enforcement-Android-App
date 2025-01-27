package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.boryanz.upszakoni.ui.navigation.destinations.OvertimeInputDestination

@Composable
fun BonusSalaryDashboardScreen(navHostController: NavHostController) {
    BonusSalaryDashboardContent(onMonthClicked = {
        navHostController.navigate(OvertimeInputDestination(it))
    })
}