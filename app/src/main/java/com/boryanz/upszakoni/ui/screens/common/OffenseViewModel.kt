package com.boryanz.upszakoni.ui.screens.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.domain.BaseError
import com.boryanz.upszakoni.domain.fold
import com.boryanz.upszakoni.domain.offenses.OffensesProvider
import com.boryanz.upszakoni.domain.offenses.model.Offense
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OffensesUiState(
  val isLoading: Boolean = false,
  val offenses: List<Offense> = emptyList()
)

sealed interface OffenseUiEvent {
  data class Failure(val baseError: BaseError) : OffenseUiEvent
}

class OffenseViewModel(private val offensesRepo: OffensesProvider) : ViewModel() {

  private val _uiState: MutableStateFlow<OffensesUiState> = MutableStateFlow(OffensesUiState())
  val uiState = _uiState.asStateFlow()

  private val _uiEvent: MutableSharedFlow<OffenseUiEvent> = MutableSharedFlow()
  val uiEvent = _uiEvent.asSharedFlow()

  fun getOffenses(lawId: String) {
    _uiState.update { it.copy(isLoading = true) }

    viewModelScope.launch {
      offensesRepo.getOffensesByType(lawId).fold(
        onSuccess = { result ->
          _uiState.update { it.copy(offenses = result, isLoading = false) }
        },
        onFailure = {
          _uiState.update { uiState ->
            uiState.copy(isLoading = false, offenses = emptyList())
          }
          _uiEvent.emit(OffenseUiEvent.Failure(it))
        }
      )
    }
  }
}