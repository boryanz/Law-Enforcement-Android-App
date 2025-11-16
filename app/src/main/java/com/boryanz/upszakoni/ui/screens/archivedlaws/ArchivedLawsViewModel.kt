package com.boryanz.upszakoni.ui.screens.archivedlaws

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.analytics.AnalyticsLogger
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import com.boryanz.upszakoni.domain.LawsUseCase
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import com.boryanz.upszakoni.ui.screens.common.UiState
import com.boryanz.upszakoni.utils.removePdfExtension
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArchivedLawsViewModel(
  private val getLawsUseCase: LawsUseCase,
  private val localStorage: SharedPrefsManager,
  private val analyticsLogger: AnalyticsLogger,
) : ViewModel() {

  private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
  val uiState = _uiState.asStateFlow()

  init {
    analyticsLogger.logScreenEntry("Archived Laws Screen")
  }

  fun onUiEvent(event: ScreenAction) {
    viewModelScope.launch {
      when (event) {
        is ScreenAction.GetLaws -> {
          val laws = getLawsUseCase().map { it.removePdfExtension() }
          val availableLaws = laws.filterAvailableLaws()
          _uiState.update { UiState(availableLaws) }
        }

        is ScreenAction.LawSwiped -> {
          localStorage.removeArchivedLaw(event.lawName)
          _uiState.update { it.copy(laws = uiState.value.laws.filterAvailableLaws()) }
        }
      }
    }
  }

  private fun List<String>.filterAvailableLaws() =
    filter { localStorage.contains(it) }
}