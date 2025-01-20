package com.boryanz.upszakoni.ui.screens.bonussalary.parameters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.boryanz.upszakoni.ui.navigation.navigationwrapper.NavigationWrapperImpl
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersUiEvent.AbsenceLimitChanged
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersUiEvent.OvertimeLimitChanged
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersUiEvent.SaveParametersClicked
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

sealed interface BonusSalaryParametersUiEvent {
    data class OvertimeLimitChanged(val value: String) : BonusSalaryParametersUiEvent
    data class AbsenceLimitChanged(val value: String) : BonusSalaryParametersUiEvent
    data object SaveParametersClicked : BonusSalaryParametersUiEvent
}

@Composable
fun BonusSalaryParametersScreen(
    navController: NavHostController,
    onBackClicked: () -> Unit,
) {

    val navigator = remember { NavigationWrapperImpl(navController) }
    val viewmodel =
        koinViewModel<BonusSalaryParametersViewModel>(parameters = { parametersOf(navigator) })
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()

    BonusSalaryParametersContent(
        uiState = uiState,
        onSaveClicked = { viewmodel.onUiEvent(SaveParametersClicked) },
        onAbsenceDaysLimitValueChanged = { viewmodel.onUiEvent(AbsenceLimitChanged(it)) },
        onOvertimeHoursValueChanged = { viewmodel.onUiEvent(OvertimeLimitChanged(it)) },
        onBackClicked = onBackClicked
    )
}

@Preview
@Composable
private fun BonusSalaryParametersContentPreview() {
    KataSampleAppTheme {
        BonusSalaryParametersContent(
            onAbsenceDaysLimitValueChanged = {},
            onSaveClicked = {},
            onOvertimeHoursValueChanged = {},
            uiState = BonusSalaryParametersUiState(),
            onBackClicked = {}
        )
    }
}