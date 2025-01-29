package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.AutoAdvancePager
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
) {
    UpsScaffold(
        topBarTitle = { Text("Прекувремени часови") },
        navigationIcon = {
            Icons.Back(onClick = onBackClicked)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(12.dp),
            verticalArrangement = Arrangement.Top,
        ) {
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
            Spacer.Vertical(4.dp)
            Text(
                "* Во просек, потребни се ${uiState.averageHoursPerMonth} прекувремени часови месечно да се оствари право на бонус плата.",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer.Vertical(16.dp)
            Text(
                "* ПРЧ часовите се пресметуваат по формула (основни прекувремени часови x 1.4)",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
private fun BonusSalaryDashboardContentPreview() {
    KataSampleAppTheme {
        BonusSalaryDashboardContent(BonusSalaryDashboardUiState(), {}, {})
    }
}