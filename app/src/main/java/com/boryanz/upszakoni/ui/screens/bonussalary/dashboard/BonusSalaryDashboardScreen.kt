package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.boryanz.upszakoni.ui.components.Loader
import com.boryanz.upszakoni.ui.navigation.destinations.NonWorkingDaysInfoDestination
import com.boryanz.upszakoni.ui.navigation.destinations.OvertimeInputDestination
import org.koin.androidx.compose.koinViewModel

@Composable
fun BonusSalaryDashboardScreen(
    navHostController: NavHostController,
    onEditClicked: () -> Unit,
    onBackClicked: () -> Unit,
) {

    val viewModel = koinViewModel<BonusSalaryDashboardViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.onUiEvent(BonusSalaryDashboardUiEvent.FetchMonthlyStats)
    }

    if (uiState.isLoading) {
        Loader()
    }

    BonusSalaryDashboardContent(
        uiState = uiState,
        onMonthClicked = {
            navHostController.navigate(OvertimeInputDestination(it))
        },
        onBackClicked = onBackClicked,
        onDeleteAllClicked = {
            viewModel.onUiEvent(BonusSalaryDashboardUiEvent.DeleteAll)
            onBackClicked()
        },
        onEditClicked = onEditClicked,
        onNonWorkingDaysClicked = { navHostController.navigate(NonWorkingDaysInfoDestination(it)) }
    )
}