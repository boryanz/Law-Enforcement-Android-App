package com.boryanz.upszakoni.ui.screens.ai.addprompt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.domain.ai.AiGenerationChecker
import com.boryanz.upszakoni.domain.ai.PromptType
import com.boryanz.upszakoni.ui.screens.ai.addprompt.AddPromptUiEvent.GenerateClicked
import com.boryanz.upszakoni.ui.screens.ai.addprompt.AddPromptUiEvent.OnCreate
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
    val fullPrompt: String
  ) : AddPromptEvent
}

data class AddPromptUiState(
  val prompt: String = "",
  val examplePrompt: String = PromptType.COMPLAINT.prompt,
  val hasPromptError: Boolean = false,
  val aiGenerationsUsed: Int = 0,
  val isAiLimitReached: Boolean = false,
)

const val MAX_AI_GENERATIONS_PER_DAY = 5


class AddPromptViewModel(private val aiGenerationChecker: AiGenerationChecker) : ViewModel() {

  private val embgRegex = Regex("(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[0-2])[0-9]{9}")
  val idCardRegex = Regex("\\b[A-Z]\\d{7}\\b")

  private val _uiState = MutableStateFlow(AddPromptUiState())
  val uiState = _uiState.asStateFlow()

  private val _event = MutableSharedFlow<AddPromptEvent>()
  val event = _event.asSharedFlow()

  fun onUiEvent(event: AddPromptUiEvent) = viewModelScope.launch {
    when (event) {
      OnCreate -> initializeAiGenerationCounter()
      is PromptChanged -> {
        val hasError = event.value.isBlank()
            || embgRegex.containsMatchIn(event.value)
            || idCardRegex.containsMatchIn(event.value)

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
            examplePrompt = uiState.value.examplePrompt
          )
        )
      }

      is OnPromptTypeChanged -> _uiState.update { it.copy(examplePrompt = event.prompt) }
    }
  }

  private fun initializeAiGenerationCounter() {
    val generationsUsed = aiGenerationChecker.generationsUsed()
    val isLimitReached = generationsUsed >= MAX_AI_GENERATIONS_PER_DAY
    _uiState.update {
      it.copy(
        aiGenerationsUsed = generationsUsed,
        isAiLimitReached = isLimitReached
      )
    }
  }

}
