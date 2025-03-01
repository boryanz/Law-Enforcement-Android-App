package com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.Sick
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.Button
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.Spacer.Vertical
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.components.input.TextFieldInput
import com.boryanz.upszakoni.ui.theme.UpsTheme

data class UiState(
    val hasOvertimeError: Boolean = false,
    val isSickDay: Boolean = false,
    val isPaidLeave: Boolean = false,
)

@Composable
fun NewOvertimeInputContent(
    uiState: UiState,
    month: String,
    dayInMonth: String,
    onOvertimeHoursChanged: (String) -> Unit,
    onPaidLeaveClicked: () -> Unit,
    onSickDayClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    onBackClicked: () -> Unit,
) {
    UpsScaffold(
        topBarTitle = { Text("$dayInMonth $month") },
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
                    labelText = "Прекувремени на ден $dayInMonth $month",
                    value = "",
                    isError = false,
                    textStyle = MaterialTheme.typography.titleLarge,
                    labelTextStyle = MaterialTheme.typography.bodyMedium,
                    onValueChanged = onOvertimeHoursChanged,
                    trailingIcon = {
                        Icons.Base(imageVector = androidx.compose.material.icons.Icons.Filled.AccessTime) { }
                    }
                )
                Vertical(4.dp)
                Button.PrimaryOutlinedWithIcon(
                    title = "Платено отсуство",
                    imageVector = androidx.compose.material.icons.Icons.Filled.AirplanemodeActive,
                    onClick = onPaidLeaveClicked
                )
                Vertical(4.dp)
                Button.PrimaryOutlinedWithIcon(
                    title = "Боледување",
                    isEnabled = !uiState.isSickDay,
                    imageVector = androidx.compose.material.icons.Icons.Filled.Sick,
                    onClick = onSickDayClicked
                )
            }
            Column {
                Button.Primary(
                    isEnabled = !uiState.hasOvertimeError,
                    title = "Во ред",
                    onClick = { onSaveClicked() }
                )
            }
        }
        Vertical(8.dp)
    }
}


@PreviewLightDark
@Preview
@Composable
private fun NewOvertimeInputScreenPreview() {
    UpsTheme {
        NewOvertimeInputContent(
            uiState = UiState(),
            month = "Март",
            dayInMonth = "1",
            onOvertimeHoursChanged = {},
            onPaidLeaveClicked = {},
            onSickDayClicked = {},
            onSaveClicked = {},
            onBackClicked = {})
    }
}