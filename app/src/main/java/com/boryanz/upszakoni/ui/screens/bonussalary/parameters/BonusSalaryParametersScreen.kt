package com.boryanz.upszakoni.ui.screens.bonussalary.parameters

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersUiEvent.AbsenceLimitChanged
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersUiEvent.OvertimeLimitChanged
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersUiEvent.SaveParametersClicked
import com.boryanz.upszakoni.ui.theme.UpsTheme
import org.koin.androidx.compose.koinViewModel

sealed interface BonusSalaryParametersUiEvent {
    data class OvertimeLimitChanged(val value: String) : BonusSalaryParametersUiEvent
    data class AbsenceLimitChanged(val value: String) : BonusSalaryParametersUiEvent
    data class SaveParametersClicked(val onParamSaved: () -> Unit) : BonusSalaryParametersUiEvent
    data object FetchData : BonusSalaryParametersUiEvent
}

@Composable
fun BonusSalaryParametersScreen(
    onParametersSaved: () -> Unit,
) {

    BackHandler {
        /*block back button*/
    }

    val viewmodel =
        koinViewModel<BonusSalaryParametersViewModel>()
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewmodel.onUiEvent(BonusSalaryParametersUiEvent.FetchData)
    }

    BonusSalaryParametersContent(
        uiState = uiState,
        onSaveClicked = { viewmodel.onUiEvent(SaveParametersClicked(onParametersSaved)) },
        onAbsenceDaysLimitValueChanged = { viewmodel.onUiEvent(AbsenceLimitChanged(it)) },
        onOvertimeHoursValueChanged = { viewmodel.onUiEvent(OvertimeLimitChanged(it)) },
    )
}

@Preview
@Composable
private fun BonusSalaryParametersContentPreview() {
    UpsTheme {
        BonusSalaryParametersContent(
            onAbsenceDaysLimitValueChanged = {},
            onSaveClicked = {},
            onOvertimeHoursValueChanged = {},
            uiState = BonusSalaryParametersUiState(),
        )
    }
}