package com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Sick
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    text = "Вкупно прекувремени часови за месец $month",
                    textAlign = TextAlign.Start
                )
                TextFieldInput.BaseOutline(
                    labelText = "Прекувремени",
                    value = "",
                    onValueChanged = onOvertimeHoursValueChanged,
                    trailingIcon = {
                        Icons.Base(imageVector = androidx.compose.material.icons.Icons.Filled.AccessTime) { }
                    }
                )
                Vertical(8.dp)
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    text = "Искористени денови од боледување",
                    textAlign = TextAlign.Start
                )
                TextFieldInput.BaseOutline(
                    labelText = "Боледување",
                    value = "",
                    onValueChanged = onSickDaysValueChanged,
                    trailingIcon = {
                        Icons.Base(imageVector = androidx.compose.material.icons.Icons.Filled.Sick) { }
                    }
                )
                Vertical(8.dp)
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    text = "Искористено платено отсуство и прч денови",
                    textAlign = TextAlign.Start
                )
                TextFieldInput.BaseOutline(
                    labelText = "Платено отсуство",
                    value = "",
                    onValueChanged = onAbsenceDaysValueChanged,
                    trailingIcon = {
                        Icons.Base(imageVector = androidx.compose.material.icons.Icons.Filled.ArrowOutward) { }
                    }
                )
            }

            Column {
                Button.Primary(
                    isEnabled = true, title = "Во ред",
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
            "Јануари",
            onBackClicked = {},
            onSaveClicked = {},
            onOvertimeHoursValueChanged = {},
            onSickDaysValueChanged = {},
            onAbsenceDaysValueChanged = {})
    }
}