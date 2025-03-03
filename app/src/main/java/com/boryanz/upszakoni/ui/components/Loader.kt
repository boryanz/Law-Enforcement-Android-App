package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.boryanz.upszakoni.ui.theme.Base100
import com.boryanz.upszakoni.ui.theme.BaseContent1

@Composable
fun Loader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BaseContent1)
            .pointerInput(Unit) {
                // Consume pointer events to prevent interaction with buttons below
                // This prevents clicks from reaching the buttons below the overlay
            },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Base100
        )
    }
}