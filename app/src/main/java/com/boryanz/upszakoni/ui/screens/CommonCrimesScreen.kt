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
import com.boryanz.upszakoni.ui.components.ItemWithDescription
import com.boryanz.upszakoni.ui.components.UpsScaffold

typealias PdfData = (String, List<Int>) -> Unit

@Composable
fun CommonCrimes(
    title: String,
    commonCrimesItems: List<Offense>,
    onClick: PdfData,
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
                    ItemWithDescription(
                        isEnabled = true,
                        title = it.title,
                        description = it.description,
                        onClick = { onClick(it.lawName, it.pagesToLoad) }
                    )
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}