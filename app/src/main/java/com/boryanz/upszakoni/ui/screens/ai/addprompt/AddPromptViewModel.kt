package com.boryanz.upszakoni.ui.screens.ai.addprompt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.domain.ai.PromptType
import com.boryanz.upszakoni.ui.screens.ai.addprompt.AddPromptUiEvent.GenerateClicked
import com.boryanz.upszakoni.ui.screens.ai.addprompt.AddPromptUiEvent.OnPromptTypeChanged
import com.boryanz.upszakoni.ui.screens.ai.addprompt.AddPromptUiEvent.PromptChanged
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AddPromptEvent {
  data class GenerateDocument(
    val examplePrompt: String,
    val fullPrompt: String,
    val type: String,
  ) : AddPromptEvent
}

data class AddPromptUiState(
  val prompt: String = "",
  val type: String = PromptType.COMPLAINT.name,
  val examplePrompt: String = PromptType.COMPLAINT.prompt,
  val hasPromptError: Boolean = false,
)

fun interface Validator {
  fun validate(input: String): Boolean
}

val embgValidator = Validator { input ->
  Regex("(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[0-2])[0-9]{9}").containsMatchIn(input)
}

val idCardValidator = Validator { input ->
  Regex("\\b[A-Z]\\d{7}\\b").containsMatchIn(input)
}

class AddPromptViewModel() : ViewModel() {

  private val _uiState = MutableStateFlow(AddPromptUiState())
  val uiState = _uiState.asStateFlow()

  private val _event = MutableSharedFlow<AddPromptEvent>()
  val event = _event.asSharedFlow()

  fun onUiEvent(event: AddPromptUiEvent) = viewModelScope.launch {
    when (event) {
      is PromptChanged -> {
        val hasError = event.value.isBlank()
            || embgValidator.validate(event.value)
            || idCardValidator.validate(event.value)

        _uiState.update {
          it.copy(
            prompt = event.value,
            hasPromptError = hasError
          )
        }
      }

      GenerateClicked -> {
        _event.emit(
          AddPromptEvent.GenerateDocument(
            fullPrompt = uiState.value.prompt,
            examplePrompt = uiState.value.examplePrompt,
            type = uiState.value.type
          )
        )

      }

      is OnPromptTypeChanged -> _uiState.update {
        it.copy(
          examplePrompt = event.prompt,
          type = event.type
        )
      }
    }
  }
}
