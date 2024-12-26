package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.data.model.TitleItem
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme

@Composable
fun BasicTitleListScreen(
    topBarTitle: String,
    items: List<TitleItem>,
    onBackClicked: () -> Unit,
) {
    UpsScaffold(
        topBarTitle = { Text(text = topBarTitle, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            Icons.Back(onClick = onBackClicked)
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            LazyColumn {
                items(items) {
                    TitleItem(it.title, onClick = { /* Do nothing currently */ })
                    Spacer.Vertical(4.dp)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PoliceAuthorityScreenPreview() {
    KataSampleAppTheme {
        BasicTitleListScreen(
            topBarTitle = "Овластувања",
            items = listOf(TitleItem("Basic title")),
            onBackClicked = {  }

        )
    }
}