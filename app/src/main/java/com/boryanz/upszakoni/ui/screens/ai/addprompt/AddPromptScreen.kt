package com.boryanz.upszakoni.ui.screens.ai.addprompt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.domain.ai.PromptType
import com.boryanz.upszakoni.ui.components.Button
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.SegmentedControl
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.components.input.TextFieldInput
import com.boryanz.upszakoni.ui.screens.ai.addprompt.AddPromptUiEvent.GenerateClicked
import com.boryanz.upszakoni.ui.screens.ai.addprompt.AddPromptUiEvent.PromptChanged
import com.boryanz.upszakoni.utils.collectEvents
import org.koin.androidx.compose.koinViewModel

sealed interface AddPromptUiEvent {

  data object OnCreate : AddPromptUiEvent
  data class PromptChanged(val value: String) : AddPromptUiEvent

  data class OnPromptTypeChanged(val prompt: String) : AddPromptUiEvent
  data object GenerateClicked : AddPromptUiEvent
}


@Composable
fun AddPromptScreen(
  onBackClicked: () -> Unit,
  onGenerateDocumentClicked: (String, String) -> Unit,
  viewModel: AddPromptViewModel = koinViewModel()
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  viewModel.event.collectEvents { event ->
    when (event) {
      is AddPromptEvent.GenerateDocument -> {
        onGenerateDocumentClicked(event.fullPrompt, event.examplePrompt)
      }
    }
  }


  UpsScaffold(
    topBarTitle = { Text(stringResource(R.string.ai_prompt_title_text)) },
    navigationIcon = { Icons.Back(onClick = onBackClicked) }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(horizontal = 12.dp)
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.Top
    ) {
      Spacer.Vertical(6.dp)
      SegmentedControl(
        options = listOf(
          PromptType.COMPLAINT.title,
          PromptType.PUBLIC_ORDER_AND_PEACE.title,
          PromptType.SECURING_CRIME_SCENE.title,
        ),
        onSelectionChanged = {
          viewModel.onUiEvent(AddPromptUiEvent.OnPromptTypeChanged(prompt = it))
        }
      )
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
