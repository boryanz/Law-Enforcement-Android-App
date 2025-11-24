package com.boryanz.upszakoni.ui.screens.ai

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.UpsScaffold

@Composable
fun DocumentScreen(
  document: String,
  onBackClicked: () -> Unit,
) {
  val document = remember { document }
  UpsScaffold(
    topBarTitle = { Text(stringResource(R.string.ai_generated_document_title)) },
    navigationIcon = { Icons.Back(onClick = onBackClicked) }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(horizontal = 12.dp, vertical = 16.dp)
        .verticalScroll(rememberScrollState())
    ) {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = AnnotatedString.fromHtml(htmlString = document)
      )
    }
  }
}