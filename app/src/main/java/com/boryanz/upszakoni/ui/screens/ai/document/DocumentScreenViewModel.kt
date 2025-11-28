package com.boryanz.upszakoni.ui.screens.ai.document

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import com.boryanz.upszakoni.domain.ai.AiGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//Poplaka za glasna muzika na ulica Hristo Botev 6. Izvrsen e razgovor so prijavitelot I prijaveniot. Prijavenoto lice se soglasi da ja namali muzikata I bese usno predupredeno dokolku ne nnamali, ke se postapi po zakonot za javen red I mir

data class DocumentScreenUiState(
  val isLoading: Boolean = false,
  val dataChunk: String = ""
)

class DocumentScreenViewModel(
  private val aiGenerator: AiGenerator,
  private val sharedPrefsManager: SharedPrefsManager,
) : ViewModel() {

  private val _uiState: MutableStateFlow<DocumentScreenUiState> =
    MutableStateFlow(DocumentScreenUiState())
  val uiState = _uiState.asStateFlow()

  fun startDocumentGeneration(fullPrompt: String, examplePrompt: String) = viewModelScope.launch {
    _uiState.update { it.copy(isLoading = true) }
    aiGenerator.generateDocument(examplePrompt, fullPrompt).collect { newChunk ->
      _uiState.update { uiState ->
        uiState.copy(
          isLoading = false,
          dataChunk = uiState.dataChunk + newChunk.text.orEmpty()
        )
      }
    }
    sharedPrefsManager.incrementAiGenerationCounter()
  }
}