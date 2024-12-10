package com.boryanz.upszakoni.ui.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boryanz.upszakoni.data.DashboardItemDestination
import com.boryanz.upszakoni.data.dashboardItems
import com.boryanz.upszakoni.ui.components.UpsItem
import com.boryanz.upszakoni.ui.components.UpsScaffold


@Composable
fun DashboardScreen(
    onNavigateNext: (id: DashboardItemDestination) -> Unit
) {
    UpsScaffold(
        topBarTitle = { Text("УПС 2", fontWeight = FontWeight.ExtraBold, fontSize = 36.sp) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            LazyColumn {
                items(dashboardItems) {
                    UpsItem(
                        title = it.title,
                        drawableRes = it.drawableRes,
                        onClick = { onNavigateNext(it.id) })
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
        }
    }
}

