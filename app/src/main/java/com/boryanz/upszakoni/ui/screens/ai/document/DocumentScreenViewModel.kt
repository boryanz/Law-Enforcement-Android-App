package com.boryanz.upszakoni.ui.screens.ai.document

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.domain.ai.AiGenerator
import com.boryanz.upszakoni.utils.ConnectionUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//Poplaka za glasna muzika na ulica Hristo Botev 6. Izvrsen e razgovor so prijavitelot I prijaveniot. Prijavenoto lice se soglasi da ja namali muzikata I bese usno predupredeno dokolku ne nnamali, ke se postapi po zakonot za javen red I mir

data class DocumentScreenUiState(
  val isLoading: Boolean = false,
  val dataChunk: String? = "",
  val hasConnection: Boolean = true,
)

class DocumentScreenViewModel(
  private val aiGenerator: AiGenerator,
) : ViewModel() {

  private val _uiState: MutableStateFlow<DocumentScreenUiState> =
    MutableStateFlow(DocumentScreenUiState())
  val uiState = _uiState.asStateFlow()

  fun startDocumentGeneration(fullPrompt: String, examplePrompt: String) = viewModelScope.launch {
    if (ConnectionUtils.hasConnection().not()) {
      _uiState.update { it.copy(isLoading = false, hasConnection = false) }
      return@launch
    }
    _uiState.update { it.copy(isLoading = true) }
    aiGenerator.generateDocument(examplePrompt, fullPrompt).collect { newChunk ->
      _uiState.update { uiState ->
        uiState.copy(
          isLoading = false,
          dataChunk = uiState.dataChunk + newChunk.text.orEmpty()
        )
      }
    }
  }
}