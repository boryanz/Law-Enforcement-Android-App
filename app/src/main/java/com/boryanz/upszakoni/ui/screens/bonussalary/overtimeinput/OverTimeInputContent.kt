package com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Sick
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.Button
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.Spacer.Vertical
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.components.input.TextFieldInput
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme

@Composable
fun OverTimeInputContent(
    uiState: OverTimeInputUiState,
    month: String,
    onBackClicked: () -> Unit,
    onOvertimeHoursValueChanged: (String) -> Unit,
    onSickDaysValueChanged: (String) -> Unit,
    onAbsenceDaysValueChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
) {
    UpsScaffold(
        topBarTitle = { Text(month) },
        navigationIcon = {
            Icons.Back(onClick = onBackClicked)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.Top) {
                TextFieldInput.BaseOutline(
                    labelText = "Вкупно прекувремени за месец $month",
                    value = uiState.overtimeHours,
                    isError = uiState.hasOvertimeHoursError,
                    textStyle = MaterialTheme.typography.titleLarge,
                    labelTextStyle = MaterialTheme.typography.bodyMedium,
                    onValueChanged = onOvertimeHoursValueChanged,
                    trailingIcon = {
                        Icons.Base(imageVector = androidx.compose.material.icons.Icons.Filled.AccessTime) { }
                    }
                )
                Vertical(8.dp)
                TextFieldInput.BaseOutline(
                    labelText = "Бкупно денови боледување",
                    value = uiState.sickDays,
                    isError = uiState.hasSickDaysError,
                    textStyle = MaterialTheme.typography.titleLarge,
                    labelTextStyle = MaterialTheme.typography.bodyMedium,
                    onValueChanged = onSickDaysValueChanged,
                    trailingIcon = {
                        Icons.Base(imageVector = androidx.compose.material.icons.Icons.Filled.Sick) { }
                    }
                )
                Vertical(8.dp)
                TextFieldInput.BaseOutline(
                    labelText = "Вкупно денови платено отсуство - ПРЧ",
                    value = uiState.paidAbsenceDays,
                    isError = uiState.hasPaidAbsenceDaysError,
                    textStyle = MaterialTheme.typography.titleLarge,
                    labelTextStyle = MaterialTheme.typography.bodyMedium,
                    onValueChanged = onAbsenceDaysValueChanged,
                    trailingIcon = {
                        Icons.Base(imageVector = androidx.compose.material.icons.Icons.Filled.ArrowOutward) { }
                    }
                )
            }

            Column {
                Button.Primary(
                    isEnabled = !uiState.hasPaidAbsenceDaysError && !uiState.hasSickDaysError && !uiState.hasOvertimeHoursError,
                    title = "Во ред",
                    onClick = onSaveClicked
                )
            }
        }
    }
}

@Preview
@Composable
private fun OverTimeInputPreview() {
    KataSampleAppTheme {
        OverTimeInputContent(
            OverTimeInputUiState(),
            "Јануари",
            onBackClicked = {},
            onSaveClicked = {},
            onOvertimeHoursValueChanged = {},
            onSickDaysValueChanged = {},
            onAbsenceDaysValueChanged = {})
    }
}