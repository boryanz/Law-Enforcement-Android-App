package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.AutoAdvancePager
import com.boryanz.upszakoni.ui.components.Button
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.LazyGridLayout
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme

@Composable
fun BonusSalaryDashboardContent(
    uiState: BonusSalaryDashboardUiState,
    onMonthClicked: (String) -> Unit,
    onBackClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onDeleteAllClicked: () -> Unit,
    onNonWorkingDaysClicked: (String) -> Unit,
) {
    var isDeleteAllClicked by remember {
        mutableStateOf(false)
    }

    var resetClickCounter by remember { mutableIntStateOf(3) }

    UpsScaffold(
        topBarTitle = { Text("Прекувремени") },
        navigationIcon = {
            Icons.Back(onClick = onBackClicked)
        },
        trailingIcon = {
            Icons.Edit(onClick = onEditClicked)
            if (isDeleteAllClicked) {
                Icons.Undo(onClick = { isDeleteAllClicked = false })
            } else {
                Icons.Delete(onClick = { isDeleteAllClicked = true })
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
        ) {
            if (isDeleteAllClicked) {
                Text("Ресетирај ги прекувремените часови", textAlign = TextAlign.Start)
                Spacer.Vertical(4.dp)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "ВНИМАТЕЛНО, оваа акција не може да се поништи!!!",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer.Vertical(8.dp)
                Button.Primary(
                    title = "Кликни $resetClickCounter пати за ресетирање",
                    onClick = {
                        resetClickCounter--
                        if (resetClickCounter == 0) {
                            onDeleteAllClicked()
                            return@Primary
                        }
                    })
                Spacer.Vertical(16.dp)
            }
            Text("Годишна статистика", textAlign = TextAlign.Start)
            Spacer.Vertical(8.dp)
            uiState.sliderState?.let {
                AutoAdvancePager(uiState)
            }
            Spacer.Vertical(8.dp)
            Text("Прекувремени часови по месеци", textAlign = TextAlign.Start)
            LazyGridLayout(
                uiState = uiState,
                onClick = { onMonthClicked(it) },
                paddingValues = PaddingValues(vertical = 8.dp)
            )
            if (!uiState.nonWorkingDays.isNullOrBlank()) {
                Spacer.Vertical(8.dp)
                Button.Outlined(
                    title = "Неработни денови",
                    onClick = { onNonWorkingDaysClicked(uiState.nonWorkingDays) })
            }
        }
    }
}

@Preview
@Composable
private fun BonusSalaryDashboardContentPreview() {
    KataSampleAppTheme {
        BonusSalaryDashboardContent(BonusSalaryDashboardUiState(), {}, {}, {}, {}, {})
    }
}