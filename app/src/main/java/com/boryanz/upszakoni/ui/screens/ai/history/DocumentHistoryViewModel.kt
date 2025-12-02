package com.boryanz.upszakoni.ui.screens.ai.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryUserEvent.DeleteDialogConfirmed
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryUserEvent.DeleteDialogDismissed
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryUserEvent.DocumentDeleteClicked
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryUserEvent.OnCreate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DocumentHistoryViewModel : ViewModel() {

  private val _uiState: MutableStateFlow<DocumentHistoryUiState> = MutableStateFlow(DocumentHistoryUiState())
  val uiState = _uiState.asStateFlow()

  fun onUserEvent(event: DocumentHistoryUserEvent) = viewModelScope.launch {
    when (event) {
      DeleteDialogConfirmed -> TODO()
      DeleteDialogDismissed -> TODO()
      is DocumentDeleteClicked -> TODO()
      OnCreate -> TODO()
      DocumentHistoryUserEvent.FABClicked -> TODO()
    }
  }
}