package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.tooling.preview.Preview
import com.boryanz.upszakoni.ui.theme.Base100
import com.boryanz.upszakoni.ui.theme.BaseContent
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme

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
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = BaseContent, titleContentColor = Base100),
                navigationIcon = {
                    if (navigationIcon != null) {
                        navigationIcon()
                    }
                },
            )
        },
        content = { paddingValues ->
            content(paddingValues)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun UpsScaffoldPreview() {
    KataSampleAppTheme {
        TopAppBar(
            title = { Text("Something", color = Base100) },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = BaseContent, titleContentColor = Base100),
            navigationIcon = { }
        )
    }
}