package com.boryanz.upszakoni.ui.screens.laws

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.domain.BaseError
import com.boryanz.upszakoni.domain.fold
import com.boryanz.upszakoni.domain.laws.LawsProvider
import com.boryanz.upszakoni.domain.remoteconfig.FirebaseRemoteConfig
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfig
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import com.boryanz.upszakoni.ui.screens.common.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface LawsEvent {
  data class Failure(val error: BaseError) : LawsEvent
  data class PdfReady(val pdfPath: String) : LawsEvent
}

class LawsViewModel(
  private val remoteConfigRepository: FirebaseRemoteConfig,
  private val lawsRepository: LawsProvider
) : ViewModel() {

  private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
  val uiState = _uiState.asStateFlow()

  private val _uiEvent: MutableSharedFlow<LawsEvent> = MutableSharedFlow()
  val event = _uiEvent.asSharedFlow()

  val featureFlagsState: StateFlow<RemoteConfig>
    get() = remoteConfigRepository.remoteConfigState

  fun onUiEvent(event: ScreenAction) {
    viewModelScope.launch {
      when (event) {
        is ScreenAction.GetLaws -> {
          lawsRepository.getLaws().fold(
            onSuccess = { response ->
              _uiState.update { UiState(response) }
            },
            onFailure = { error ->
              delay(100L)
              _uiEvent.emit(LawsEvent.Failure(error))
            }
          )
        }

        is ScreenAction.LawClicked -> {
          val pdfFileResult = lawsRepository.getLaw(event.fileName, event.id)
          pdfFileResult.fold(
            onSuccess = { _uiEvent.emit(LawsEvent.PdfReady(it)) },
            onFailure = { _uiEvent.emit(LawsEvent.Failure(it)) }
          )
        }
      }
    }
  }
}