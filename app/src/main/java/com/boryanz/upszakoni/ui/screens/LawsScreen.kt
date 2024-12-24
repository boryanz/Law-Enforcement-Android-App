package com.boryanz.upszakoni.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.data.NavigationDrawerDestination
import com.boryanz.upszakoni.ui.components.NavigationDrawer
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.TitleItem

@Composable
fun LawsScreen(
    onLawClick: (String) -> Unit,
    onItemClick: (NavigationDrawerDestination) -> Unit,
) {
    NavigationDrawer(
        screenTitle = "Закони",
        onItemClicked = { onItemClick(it) }
    ) {
        val context = LocalContext.current
        val laws by remember {
            mutableStateOf(
                context.assets.list("")?.mapNotNull { it }.orEmpty().filter { it.contains(".pdf") }
            )
        }
        var searchQuery by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                label = { Text("Пребарувај") }
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            LazyColumn {
                items(laws.filter { it.contains(searchQuery, ignoreCase = true) }, key = { it }) {
                    TitleItem(
                        isEnabled = true,
                        title = it.replace(".pdf", "").trim(),
                        onClick = { onLawClick(it) })
                    Spacer.Vertical(4.dp)
                }
            }
        }
    }
}
