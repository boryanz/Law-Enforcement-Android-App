package com.boryanz.upszakoni.ui.screens.ai.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.theme.UpsTheme
import kotlinx.serialization.Serializable

@Serializable
data class GeneratedDocumentOverviewScreenDestination(val content: String)

@Composable
fun GeneratedDocumentOverviewScreen(
  content: String,
  onBackClicked: () -> Unit
) {
  UpsScaffold(
    topBarTitle = { Text("Генерирана Белешка") },
    navigationIcon = { Icons.Back(onClick = onBackClicked) }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(horizontal = 8.dp, vertical = 16.dp)
        .verticalScroll(rememberScrollState())
    ) {
      Text(text = AnnotatedString.fromHtml(content))
    }
  }
}

@Preview
@Composable
private fun GeneratedDocumentScreenPreview() {
  UpsTheme {
    GeneratedDocumentOverviewScreen("Тепачка на две лица, бла бла бла") { }
  }
}