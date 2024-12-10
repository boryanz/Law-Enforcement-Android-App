package com.boryanz.upszakoni.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.TitleItem
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme

@Composable
fun LawsScreen(onClick: (String) -> Unit) {
    UpsScaffold(
        topBarTitle = { Text(text = "Закони", fontWeight = FontWeight.Bold) },
    ) { contentPadding ->

        val context = LocalContext.current
        val laws by remember {
            mutableStateOf(
                context.assets.list("")?.mapNotNull { it }.orEmpty().filter { it.contains(".pdf") })
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            LazyColumn {
                items(laws) {
                    TitleItem(isEnabled = true, title = it, onClick = { onClick(it) })
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
        PoliceAuthorityScreen()
    }
}