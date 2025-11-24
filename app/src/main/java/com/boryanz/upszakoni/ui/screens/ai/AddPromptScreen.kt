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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    topBarTitle = { Text("Креирај белешка") },
    navigationIcon = { Icons.Back(onClick = onBackClicked) }
  ) { paddingValues ->
    if (uiState.isGenerating) {
      // Loading state
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
          text = "Службената белешка се генерира...",
          style = MaterialTheme.typography.bodyMedium
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
          labelText = "Опишете го настанот",
          value = uiState.prompt,
          isError = uiState.hasPromptError,
          textStyle = MaterialTheme.typography.bodyMedium,
          labelTextStyle = MaterialTheme.typography.bodyMedium,
          onValueChanged = { viewModel.onUiEvent(PromptChanged(it)) },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
          hint = "Пример: ",
          maxLines = 12
        )

        Spacer.Vertical(12.dp)

        Column(
          modifier = Modifier
            .fillMaxWidth(),
          verticalArrangement = Arrangement.Bottom
        ) {
          Button.Primary(
            title = "Креирај белешка",
            isEnabled = uiState.prompt.isNotBlank(),
            onClick = { viewModel.onUiEvent(GenerateClicked) }
          )

          Spacer.Vertical(12.dp)
        }
      }
    }
  }
}
