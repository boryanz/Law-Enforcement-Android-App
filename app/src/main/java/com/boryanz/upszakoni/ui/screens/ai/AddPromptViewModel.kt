package com.boryanz.upszakoni.ui.screens.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed interface AddPromptUiEvent {
  data class PromptChanged(val value: String) : AddPromptUiEvent
  data object GenerateClicked : AddPromptUiEvent
}

sealed interface AddPromptEvent {
  data class DocumentGenerated(val document: String) : AddPromptEvent
}

data class AddPromptUiState(
  val prompt: String = "",
  val isGenerating: Boolean = false,
  val hasPromptError: Boolean = false,
  val aiGenerationsUsed: Int = 0,
  val isAiLimitReached: Boolean = false,
)

const val MAX_AI_GENERATIONS_PER_DAY = 5
const val DATE_FORMAT = "yyyy-MM-dd"

class AddPromptViewModel(
  private val sharedPrefsManager: SharedPrefsManager
) : ViewModel() {
  private val _uiState = MutableStateFlow(AddPromptUiState())
  val uiState = _uiState.asStateFlow()

  private val _event = MutableSharedFlow<AddPromptEvent>()
  val event = _event.asSharedFlow()

  init {
    initializeAiGenerationCounter()
  }

  private fun initializeAiGenerationCounter() {
    val today = getCurrentDate()
    val lastCounterDate = sharedPrefsManager.getAiGenerationCounterDate()

    // Reset counter if it's a new day
    if (lastCounterDate != today) {
      sharedPrefsManager.setAiGenerationCounterDate(today)
      // Reset counter by setting it to 0 (we don't have a direct reset method)
      // We'll handle this in the getter by checking the date
    }

    val generationsUsed = sharedPrefsManager.getAiGenerationsUsedToday()
    val isLimitReached = generationsUsed >= MAX_AI_GENERATIONS_PER_DAY

    _uiState.update {
      it.copy(
        aiGenerationsUsed = generationsUsed,
        isAiLimitReached = isLimitReached
      )
    }
  }

  private fun getCurrentDate(): String {
    val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return sdf.format(Date())
  }

  fun onUiEvent(event: AddPromptUiEvent) {
    when (event) {
      is AddPromptUiEvent.PromptChanged -> {
        _uiState.update {
          it.copy(
            prompt = event.value,
            hasPromptError = event.value.isBlank()
          )
        }
      }

      AddPromptUiEvent.GenerateClicked -> {
        if (_uiState.value.prompt.isNotBlank() && !_uiState.value.isAiLimitReached) {
          generateDocument(_uiState.value.prompt)
        }
      }
    }
  }

  private fun generateDocument(userPrompt: String) = viewModelScope.launch(Dispatchers.IO) {
    _uiState.update { it.copy(isGenerating = true) }

    try {
      val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-2.5-flash")

      val systemPrompt = AI_SYSTEM_PROMPT + userPrompt

      val response = model.generateContent(systemPrompt)
      val generatedDocument = response.text.orEmpty()

      sharedPrefsManager.incrementAiGenerationCounter()
      val newGenerationsUsed = sharedPrefsManager.getAiGenerationsUsedToday()
      val isLimitReached = newGenerationsUsed >= MAX_AI_GENERATIONS_PER_DAY

      _uiState.update {
        it.copy(
          isGenerating = false,
          aiGenerationsUsed = newGenerationsUsed,
          isAiLimitReached = isLimitReached
        )
      }
      _event.emit(AddPromptEvent.DocumentGenerated(generatedDocument))
    } catch (e: Exception) {
      _uiState.update { it.copy(isGenerating = false) }
    }
  }
}
