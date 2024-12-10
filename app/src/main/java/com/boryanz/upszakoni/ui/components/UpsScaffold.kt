package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsScaffold(
    topBarTitle: @Composable () -> Unit,
    navigationIcon: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = topBarTitle,
                navigationIcon = {
                    if (navigationIcon != null) {
                        navigationIcon()
                    }
                }
            )
        },
        content = { paddingValues ->
            content(paddingValues)
        }
    )
}