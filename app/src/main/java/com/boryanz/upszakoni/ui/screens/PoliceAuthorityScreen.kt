package com.boryanz.upszakoni.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.data.policeAuthorities
import com.boryanz.upszakoni.ui.components.PoliceAuthorityItem
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme

@Composable
fun PoliceAuthorityScreen() {
    UpsScaffold(
        topBarTitle = "Полициски овластувања",
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
                items(policeAuthorities) {
                    PoliceAuthorityItem(it.title)
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