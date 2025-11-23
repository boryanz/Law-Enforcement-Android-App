package com.boryanz.upszakoni.ui.screens.ai

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.UpsScaffold

@Composable
fun DocumentScreen(
  document: String,
  onBackClicked: () -> Unit,
) {
  UpsScaffold(
    topBarTitle = { Text("Генерирана белешка") },
    navigationIcon = { Icons.Back(onClick = onBackClicked) }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(horizontal = 12.dp, vertical = 16.dp)
        .verticalScroll(rememberScrollState())
    ) {
      Text(text = document)
    }
  }
}
