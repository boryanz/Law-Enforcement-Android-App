package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.AutoAdvancePager
import com.boryanz.upszakoni.ui.components.LazyGridLayout
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme

@Composable
fun BonusSalaryDashboardContent(onMonthClicked: (String) -> Unit) {
    UpsScaffold(
        topBarTitle = { Text("Прекувремени") }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(12.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            Text("Годишна статистика", textAlign = TextAlign.Start)
            Spacer.Vertical(8.dp)
            AutoAdvancePager(listOf(Color.LightGray, Color.Black))
            Spacer.Vertical(8.dp)
            Text("Прекувремени часови по месеци", textAlign = TextAlign.Start)
            LazyGridLayout(PaddingValues(vertical = 8.dp), onClick = { onMonthClicked(it) })
        }
    }
}

@Preview
@Composable
private fun BonusSalaryDashboardContentPreview() {
    KataSampleAppTheme {
        BonusSalaryDashboardContent({})
    }
}