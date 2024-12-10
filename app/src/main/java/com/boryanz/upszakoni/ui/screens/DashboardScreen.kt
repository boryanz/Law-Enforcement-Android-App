package com.boryanz.upszakoni.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.data.DashboardItemDestination
import com.boryanz.upszakoni.data.dashboardItems
import com.boryanz.upszakoni.ui.components.IconWithTitleItem
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.theme.BaseContent2
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme


@Composable
fun DashboardScreen(
    onNavigateNext: (id: DashboardItemDestination) -> Unit
) {
    UpsScaffold(
        topBarTitle = { Text("УПС 2") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn {
                items(dashboardItems, key = { it.id }) {
                    IconWithTitleItem(
                        title = it.title,
                        drawableRes = it.drawableRes,
                        onClick = { onNavigateNext(it.id) })
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            Text(
                modifier = Modifier.border(1.dp, color = BaseContent2),
                text = "Доколку имаш нов пречистен текст, испрати на boryans.co@gmail.com",
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DashboardScreenPreview() {
    KataSampleAppTheme {
        DashboardScreen { }
    }
}

