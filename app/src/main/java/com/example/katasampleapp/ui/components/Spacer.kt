package com.example.katasampleapp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

object Spacer {

    @Composable
    fun Horizontal(value: Dp) {
        Spacer(modifier = Modifier.padding(horizontal = value))
    }

    @Composable
    fun Vertical(value: Dp) {
        Spacer(modifier = Modifier.padding(vertical = value))
    }
}