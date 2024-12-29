package com.boryanz.upszakoni.ui.screens.archivedlaws

import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.storage.sharedprefs.SharedPrefsDao
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import com.boryanz.upszakoni.ui.screens.common.UiState
import com.boryanz.upszakoni.ui.viewmodel.UpsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArchivedLawsViewModel : UpsViewModel<ScreenAction>() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    override fun onUiEvent(event: ScreenAction) {
        viewModelScope.launch {
            val sharedPrefsDao = SharedPrefsDao(event.context)
            when (event) {
                is ScreenAction.GetLaws -> {
                    val laws = event.context.assets.list("")?.mapNotNull { it }.orEmpty()
                        .filter { it.contains(".pdf") }

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