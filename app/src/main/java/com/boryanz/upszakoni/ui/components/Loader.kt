package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import com.boryanz.upszakoni.ui.theme.Base100
import com.boryanz.upszakoni.ui.theme.BaseContent

@Composable
fun Loader() {
  Box(
    modifier = Modifier
      .testTag("loader")
      .fillMaxSize()
      .pointerInput(Unit) {
        // Consume pointer events to prevent interaction with buttons below
        // This prevents clicks from reaching the buttons below the overlay
      },
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator(
      color = if (isSystemInDarkTheme()) Base100 else BaseContent
    )
  }
}