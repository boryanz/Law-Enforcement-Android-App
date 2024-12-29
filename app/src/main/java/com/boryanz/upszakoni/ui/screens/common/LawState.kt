package com.boryanz.upszakoni.ui.screens.common

import android.content.Context

sealed interface ScreenAction {
    val context: Context

    data class GetLaws(override val context: Context) : ScreenAction
    data class LawSwiped(
        override val context: Context,
        val lawName: String,
    ) : ScreenAction
}

data class UiState(val laws: List<String> = emptyList())