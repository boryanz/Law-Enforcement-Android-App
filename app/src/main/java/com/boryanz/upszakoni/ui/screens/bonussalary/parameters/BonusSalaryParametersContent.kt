package com.boryanz.upszakoni.ui.screens.bonussalary.parameters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.Button
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.components.input.TextFieldInput

data class BonusSalaryParametersUiState(
    val overtimeLimitValue: String = "150",
    val absenceDaysLimitValue: String = "21",
    val hasError: Boolean = false,
)

@Composable
fun BonusSalaryParametersContent(
    uiState: BonusSalaryParametersUiState,
    onOvertimeHoursValueChanged: (String) -> Unit,
    onAbsenceDaysLimitValueChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
    onBackClicked: () -> Unit,
) {
    UpsScaffold(
        topBarTitle = { Text("Параметри за бонус плата") },
        navigationIcon = {
            Icons.Back(onClick = onBackClicked)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(it)
                .padding(WindowInsets.systemBars.asPaddingValues()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                text = "Минимум прекувремени работни часови за остварување право на бонус плата",
                textAlign = TextAlign.Start
            )
            TextFieldInput.BaseOutline(
                labelText = "Годишни прекувремени работни часови",
                value = uiState.overtimeLimitValue,
                onValueChanged = onOvertimeHoursValueChanged
            )
            Spacer.Vertical(16.dp)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                text = "Максимум денови за користење од боледување(вклучувајки платено отсуство и прч)",
                textAlign = TextAlign.Start
            )
            TextFieldInput.BaseOutline(
                labelText = "Максимум денови на отсуство",
                value = uiState.absenceDaysLimitValue,
                onValueChanged = onAbsenceDaysLimitValueChanged
            )
            Spacer.Vertical(24.dp)
            Button.Primary(
                isEnabled = !uiState.hasError, title = "Во ред",
                onClick = onSaveClicked
            )
        }
    }
}