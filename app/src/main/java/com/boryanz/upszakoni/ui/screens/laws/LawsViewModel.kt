package com.boryanz.upszakoni.ui.screens.laws

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.analytics.AnalyticsLogger
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import com.boryanz.upszakoni.domain.fold
import com.boryanz.upszakoni.domain.laws.LawsProvider
import com.boryanz.upszakoni.domain.remoteconfig.FirebaseRemoteConfig
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfig
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import com.boryanz.upszakoni.ui.screens.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LawsViewModel(
  private val remoteConfigRepository: FirebaseRemoteConfig,
  private val localStorage: SharedPrefsManager,
  analyticsLogger: AnalyticsLogger,
  private val lawsRepository: LawsProvider
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
            it.copy(laws = uiState.value.laws.filter { localStorage.contains(it.title) })
          }
        }

        is ScreenAction.GetLaws -> {
          lawsRepository.getLaws().fold(
            onSuccess = { response ->
              _uiState.update { UiState(response) }
            },
            onFailure = {
              // send event
            }
          )
        }

        is ScreenAction.LawClicked -> {

        }
      }
    }
  }
}