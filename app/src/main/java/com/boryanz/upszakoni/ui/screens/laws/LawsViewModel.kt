package com.boryanz.upszakoni.ui.screens.laws

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.analytics.AnalyticsLogger
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import com.boryanz.upszakoni.domain.LawsUseCase
import com.boryanz.upszakoni.domain.remoteconfig.FirebaseRemoteConfig
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfig
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import com.boryanz.upszakoni.ui.screens.common.UiState
import com.boryanz.upszakoni.utils.removePdfExtension
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LawsViewModel(
  private val getLawsUseCase: LawsUseCase,
  private val remoteConfigRepository: FirebaseRemoteConfig,
  private val localStorage: SharedPrefsManager,
  private val analyticsLogger: AnalyticsLogger,
) : ViewModel() {

  private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
  val uiState = _uiState.asStateFlow()

  val featureFlagsState: StateFlow<RemoteConfig>
    get() = remoteConfigRepository.remoteConfigState

  init {
    analyticsLogger.logScreenEntry("Laws Screen")
  }

  fun onUiEvent(event: ScreenAction) {
    viewModelScope.launch {
      when (event) {
        is ScreenAction.LawSwiped -> {
          localStorage.archiveLaw(event.lawName)
          _uiState.update {
            it.copy(laws = uiState.value.laws.filterAvailableLaws())
          }
        }

        is ScreenAction.GetLaws -> {
          val laws = getLawsUseCase().map { it.removePdfExtension() }
          val availableLaws = laws.filterAvailableLaws()
          _uiState.update { UiState(availableLaws)
          }
        }
      }
    }
  }

  private fun List<String>.filterAvailableLaws() =
    filterNot { localStorage.contains(it) }
}