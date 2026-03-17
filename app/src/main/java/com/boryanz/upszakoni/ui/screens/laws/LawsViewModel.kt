package com.boryanz.upszakoni.ui.screens.laws

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.analytics.AnalyticsLogger
import com.boryanz.upszakoni.domain.LawsUseCase
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import com.boryanz.upszakoni.ui.screens.common.ScreenAction.GetLaws
import com.boryanz.upszakoni.ui.screens.common.UiState
import com.boryanz.upszakoni.utils.removePdfExtension
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LawsViewModel(
  private val getLawsUseCase: LawsUseCase,
  analyticsLogger: AnalyticsLogger,
) : ViewModel() {

  private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
  val uiState = _uiState.asStateFlow()

  init {
    analyticsLogger.logScreenEntry("Laws Screen")
  }

  fun onUiEvent(event: ScreenAction) {
    viewModelScope.launch {
      when (event) {
        is GetLaws -> {
          val laws = getLawsUseCase().map { it.removePdfExtension() }
          _uiState.update { UiState(laws) }
        }
      }
    }
  }
}
