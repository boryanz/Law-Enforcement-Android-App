package com.boryanz.upszakoni.ui.screens.ai.document

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.database.DocumentsHistoryDao
import com.boryanz.upszakoni.domain.ai.AiGenerator
import com.boryanz.upszakoni.ui.screens.ai.history.GeneratedDocument
import com.boryanz.upszakoni.utils.ConnectionUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class DocumentScreenUiState(
  val isLoading: Boolean = false,
  val dataChunk: String? = "",
  val hasConnection: Boolean = true,
  val generatedDocument: GeneratedDocument? = null,
)

class DocumentScreenViewModel(
  private val historyDao: DocumentsHistoryDao,
  private val aiGenerator: AiGenerator,
) : ViewModel() {

  private val _uiState: MutableStateFlow<DocumentScreenUiState> =
    MutableStateFlow(DocumentScreenUiState())
  val uiState = _uiState.asStateFlow()

  fun startDocumentGeneration(fullPrompt: String, examplePrompt: String, type: String) =
    viewModelScope.launch {
      if (ConnectionUtils.hasConnection().not()) {
        _uiState.update { it.copy(isLoading = false, hasConnection = false) }
        return@launch
      }
      _uiState.update { it.copy(isLoading = true) }
      aiGenerator.generateDocument(examplePrompt, fullPrompt).collect { newChunk ->
        _uiState.update { uiState ->
          val updatedText = uiState.dataChunk + newChunk.text.orEmpty()
          uiState.copy(
            isLoading = false,
            dataChunk = updatedText,
            generatedDocument = GeneratedDocument(
              title = type,
              content = updatedText,
              generatedDate = getCurrentDateFormatted()
            )
          )
        }
      }
      uiState.value.generatedDocument?.let { generatedDocument ->
        historyDao.insert(generatedDocument)
      }
    }

  private fun getCurrentDateFormatted(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return currentDate.format(formatter)
  }
}