package com.boryanz.upszakoni.ui.screens.laws

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.storage.sharedprefs.SharedPrefsDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed interface ScreenAction {
    val context: Context

    data class GetLaws(override val context: Context) : ScreenAction
    data class LawSwiped(
        override val context: Context,
        val lawName: String,
    ) : ScreenAction
}

data class UiState(val laws: List<String> = emptyList())

class LawsViewModel : UpsViewModel<ScreenAction>() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    override fun onUiEvent(event: ScreenAction) {
        viewModelScope.launch {
            val sharedPrefsDao = SharedPrefsDao(event.context)
            when (event) {
                is ScreenAction.LawSwiped -> {
                    sharedPrefsDao.archiveLaw(event.lawName)
                    _uiState.update { it.copy(laws = uiState.value.laws.filterAvailableLaws(sharedPrefsDao)) }
                }

                is ScreenAction.GetLaws -> {
                    val laws = event.context.assets.list("")?.mapNotNull { it }.orEmpty()
                        .filter { it.contains(".pdf") }

                    val availableLaws = laws.filterAvailableLaws(sharedPrefsDao)
                    _uiState.update { UiState(availableLaws) }
                }
            }
        }
    }

    private fun List<String>.filterAvailableLaws(sharedPrefsDao: SharedPrefsDao) = filterNot { sharedPrefsDao.contains(it) }
}


abstract class UpsViewModel<UiEvent> : ViewModel() {

    abstract fun onUiEvent(event: UiEvent)

}