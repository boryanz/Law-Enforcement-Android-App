package com.boryanz.upszakoni.ui.screens.laws

import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsDao
import com.boryanz.upszakoni.domain.GetLawsUseCase
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfig
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfigRepository
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import com.boryanz.upszakoni.ui.screens.common.UiState
import com.boryanz.upszakoni.ui.viewmodel.UpsViewModel
import com.boryanz.upszakoni.utils.removePdfExtension
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LawsViewModel(
    private val getLawsUseCase: GetLawsUseCase,
    private val remoteConfigRepository: RemoteConfigRepository,
) : UpsViewModel<ScreenAction>() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    val featureFlagsState: StateFlow<RemoteConfig>
        get() = remoteConfigRepository.remoteConfigState

    override fun onUiEvent(event: ScreenAction) {
        viewModelScope.launch {
            when (event) {
                is ScreenAction.LawSwiped -> {
                    SharedPrefsDao.archiveLaw(event.lawName)
                    _uiState.update {
                        it.copy(laws = uiState.value.laws.filterAvailableLaws())
                    }
                }

                is ScreenAction.GetLaws -> {
                    val laws = getLawsUseCase(event.context).map { it.removePdfExtension() }
                    val availableLaws = laws.filterAvailableLaws()
                    _uiState.update { UiState(availableLaws) }
                }
            }
        }
    }

    private fun List<String>.filterAvailableLaws() =
        filterNot { SharedPrefsDao.contains(it) }
}