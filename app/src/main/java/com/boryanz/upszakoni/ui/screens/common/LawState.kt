package com.boryanz.upszakoni.ui.screens.common

import com.boryanz.upszakoni.domain.laws.model.Law

sealed interface ScreenAction {

  data class LawClicked(val fileName: String, val id: String) : ScreenAction
  data object GetLaws : ScreenAction
}

data class UiState(val laws: List<Law> = emptyList())