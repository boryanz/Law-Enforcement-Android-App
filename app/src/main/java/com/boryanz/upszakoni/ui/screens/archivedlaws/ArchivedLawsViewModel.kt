package com.boryanz.upszakoni.ui.screens.archivedlaws

import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.domain.GetLawsUseCase
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsDao
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import com.boryanz.upszakoni.ui.screens.common.UiState
import com.boryanz.upszakoni.ui.viewmodel.UpsViewModel
import com.boryanz.upszakoni.utils.removePdfExtension
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArchivedLawsViewModel(
    private val getLawsUseCase: GetLawsUseCase = GetLawsUseCase()
) : UpsViewModel<ScreenAction>() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    override fun onUiEvent(event: ScreenAction) {
        viewModelScope.launch {
            val sharedPrefsDao = SharedPrefsDao(event.context)
            when (event) {
                is ScreenAction.GetLaws -> {
                    val laws = getLawsUseCase(event.context).map { it.removePdfExtension() }
                    val availableLaws = laws.filterAvailableLaws(sharedPrefsDao)
                    _uiState.update { UiState(availableLaws) }
                }

                is ScreenAction.LawSwiped -> {
                    sharedPrefsDao.removeArchivedLaw(event.lawName)
                    _uiState.update { it.copy(laws = uiState.value.laws.filterAvailableLaws(sharedPrefsDao)) }
                }
            }
        }
    }

    private fun List<String>.filterAvailableLaws(sharedPrefsDao: SharedPrefsDao) =
        filter { sharedPrefsDao.contains(it) }
}