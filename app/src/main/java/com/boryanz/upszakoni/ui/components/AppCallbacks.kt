package com.boryanz.upszakoni.ui.components

import androidx.compose.runtime.compositionLocalOf

data class AppCallbacks(
  val onShareAppClicked: () -> Unit,
  val onAppUpdateClicked: () -> Unit,
  val onFeedbackFormClicked: () -> Unit,
)

val LocalAppCallbacks = compositionLocalOf<AppCallbacks> {
  AppCallbacks({}, {}, {})
}
