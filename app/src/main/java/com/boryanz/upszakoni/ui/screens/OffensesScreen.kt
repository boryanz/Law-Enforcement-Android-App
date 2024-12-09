package com.boryanz.upszakoni.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.data.offensesItems
import com.boryanz.upszakoni.ui.components.OffenseItem
import com.boryanz.upszakoni.ui.components.UpsScaffold

@Composable
fun OffensesScreen() {
    UpsScaffold(
        topBarTitle = "Најчести прекршоци"
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            LazyColumn {
                items(offensesItems) {
                    OffenseItem(title = it.title, description = it.description)
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}