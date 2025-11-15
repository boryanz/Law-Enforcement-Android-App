package com.boryanz.upszakoni.ui.screens.bonussalary.parameters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.Button
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.components.input.TextFieldInput

@Composable
fun BonusSalaryParametersContent(
    uiState: BonusSalaryParametersUiState,
    onOvertimeHoursValueChanged: (String) -> Unit,
    onAbsenceDaysLimitValueChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
) {
    UpsScaffold(
        topBarTitle = { Text(stringResource(R.string.parameters_title)) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    text = stringResource(R.string.parameters_overtime_description),
                    textAlign = TextAlign.Start
                )
                TextFieldInput.BaseOutline(
                    labelText = stringResource(R.string.parameters_overtime_label),
                    value = uiState.overtimeLimitValue,
                    isError = uiState.hasOvertimeLimitError,
                    onValueChanged = onOvertimeHoursValueChanged
                )
                Spacer.Vertical(16.dp)
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    text = stringResource(R.string.parameters_absence_description),
                    textAlign = TextAlign.Start
                )
                TextFieldInput.BaseOutline(
                    labelText = stringResource(R.string.parameters_absence_label),
                    value = uiState.absenceDaysLimitValue,
                    isError = uiState.hasAbsenceDaysLimitError,
                    onValueChanged = onAbsenceDaysLimitValueChanged
                )
            }

            Column {
                Button.Primary(
                    isEnabled = !uiState.hasOvertimeLimitError && !uiState.hasAbsenceDaysLimitError,
                    title = stringResource(R.string.parameters_ok_button),
                    onClick = onSaveClicked
                )
            }
        }
    }
}