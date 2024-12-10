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
import com.boryanz.upszakoni.data.model.Offense
import com.boryanz.upszakoni.ui.components.CrimeItem
import com.boryanz.upszakoni.ui.components.UpsScaffold

@Composable
fun CommonCrimes(
    title: String,
    commonCrimesItems: List<Offense>
) {
    UpsScaffold(
        topBarTitle = { Text(text = title, fontWeight = FontWeight.Bold) }
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
                items(commonCrimesItems) {
                    CrimeItem(title = it.title, description = it.description)
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}