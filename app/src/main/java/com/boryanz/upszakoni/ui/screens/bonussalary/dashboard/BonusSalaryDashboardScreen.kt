package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.boryanz.upszakoni.ui.navigation.destinations.OvertimeInputDestination
import org.koin.androidx.compose.koinViewModel

@Composable
fun BonusSalaryDashboardScreen(
    navHostController: NavHostController,
    onBackClicked: () -> Unit,
) {

    val viewModel = koinViewModel<BonusSalaryDashboardViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.onUiEvent(BonusSalaryDashboardUiEvent.FetchMonthlyStats)
    }

    BonusSalaryDashboardContent(
        uiState = uiState,
        onMonthClicked = {
            navHostController.navigate(OvertimeInputDestination(it))
        },
        onBackClicked = onBackClicked
    )
}