package com.boryanz.upszakoni.ui.screens.common

sealed interface ScreenAction {
    data object GetLaws : ScreenAction
    data class LawSwiped(
        val lawName: String,
    ) : ScreenAction
}

data class UiState(val laws: List<String> = emptyList())