package com.boryanz.upszakoni.ui.screens.ai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.Button
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.components.input.TextFieldInput
import com.boryanz.upszakoni.ui.screens.ai.AddPromptUiEvent.GenerateClicked
import com.boryanz.upszakoni.ui.screens.ai.AddPromptUiEvent.PromptChanged
import com.boryanz.upszakoni.utils.collectEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddPromptScreen(
  onBackClicked: () -> Unit,
  onDocumentGenerated: (String) -> Unit,
  viewModel: AddPromptViewModel = koinViewModel()
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  viewModel.event.collectEvents { event ->
    when (event) {
      is AddPromptEvent.DocumentGenerated -> {
        onDocumentGenerated(event.document)
      }
    }
  }

  UpsScaffold(
    topBarTitle = { Text(stringResource(R.string.ai_prompt_title_text)) },
    navigationIcon = { Icons.Back(onClick = onBackClicked) }
  ) { paddingValues ->
    if (uiState.isGenerating) {
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
    } else {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues)
          .padding(horizontal = 12.dp)
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
      ) {
        Spacer.Vertical(6.dp)

        TextFieldInput.BaseOutline(
          modifier = Modifier.weight(1f),
          labelText = stringResource(R.string.ai_prompt_textfield_label_text),
          value = uiState.prompt,
          isError = uiState.hasPromptError,
          textStyle = MaterialTheme.typography.bodyMedium,
          labelTextStyle = MaterialTheme.typography.bodyMedium,
          onValueChanged = { viewModel.onUiEvent(PromptChanged(it)) },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
          hint = stringResource(R.string.ai_prompt_textfield_hint_text),
          maxLines = 12
        )

        Spacer.Vertical(12.dp)

        Column(
          modifier = Modifier
            .fillMaxWidth(),
          verticalArrangement = Arrangement.Bottom
        ) {
          val buttonTitle = if (uiState.isAiLimitReached) {
            stringResource(R.string.ai_limit_reached_button_text)
          } else {
            stringResource(R.string.ai_generate_button_counter_format, uiState.aiGenerationsUsed)
          }

          Button.Primary(
            title = buttonTitle,
            isEnabled = uiState.prompt.isNotBlank() && !uiState.isAiLimitReached,
            onClick = { viewModel.onUiEvent(GenerateClicked) }
          )

          Spacer.Vertical(12.dp)
        }
      }
    }
  }
}
