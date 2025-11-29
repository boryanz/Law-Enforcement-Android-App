package com.boryanz.upszakoni.ui.screens.ai.document

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import org.koin.androidx.compose.koinViewModel

@Composable
fun DocumentScreen(
  fullPrompt: String,
  examplePrompt: String,
  onBackClicked: () -> Unit,
) {
  val viewModel = koinViewModel<DocumentScreenViewModel>()
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
    viewModel.startDocumentGeneration(
      fullPrompt = fullPrompt,
      examplePrompt = examplePrompt
    )
  }

  UpsScaffold(
    topBarTitle = { Text(stringResource(R.string.ai_generated_document_title)) },
    navigationIcon = { Icons.Back(onClick = onBackClicked) }
  ) { paddingValues ->
    when {
      !uiState.hasConnection -> {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.no_internet_connection_text),
            style = MaterialTheme.typography.bodyLarge
          )
        }
      }

      uiState.isLoading -> {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          CircularProgressIndicator()
          Spacer.Vertical(16.dp)
          Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.ai_prompt_loading_text),
            style = MaterialTheme.typography.bodyLarge
          )
        }
      }

      else -> {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 12.dp, vertical = 16.dp)
            .verticalScroll(rememberScrollState())
        ) {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = AnnotatedString.fromHtml(htmlString = uiState.dataChunk.orEmpty())
          )
        }
      }
    }

  }
}