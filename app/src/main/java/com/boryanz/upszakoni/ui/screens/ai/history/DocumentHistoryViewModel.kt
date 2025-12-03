package com.boryanz.upszakoni.ui.screens.ai.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.database.BaseDocumentsDao
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryUserEvent.DeleteDialogConfirmed
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryUserEvent.DeleteDialogDismissed
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryUserEvent.DocumentDeleteClicked
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryUserEvent.OnCreate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DocumentHistoryViewModel(private val dao: BaseDocumentsDao<GeneratedDocument>) : ViewModel() {

  private val _uiState: MutableStateFlow<DocumentHistoryUiState> =
    MutableStateFlow(DocumentHistoryUiState())
  val uiState = _uiState.asStateFlow()

  fun onUserEvent(event: DocumentHistoryUserEvent) = viewModelScope.launch {
    when (event) {
      OnCreate -> {
        dao.getAll().collect { documents ->
          _uiState.update { uiState ->
            uiState.copy(generatedDocuments = documents)
          }
        }
      }

      DeleteDialogConfirmed -> {
        uiState.value.documentToDelete?.let { dao.delete(it) }
        _uiState.update { uiState -> uiState.copy(documentToDelete = null) }
      }

      DeleteDialogDismissed -> {
        _uiState.update { uiState ->
          uiState.copy(documentToDelete = null)
        }
      }

      is DocumentDeleteClicked -> {
        _uiState.update { uiState ->
          uiState.copy(documentToDelete = event.document)
        }
      }
    }
  }
}