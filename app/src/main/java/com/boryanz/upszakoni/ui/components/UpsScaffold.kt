package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.boryanz.upszakoni.ui.theme.UpsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsScaffold(
  topBarTitle: @Composable () -> Unit,
  floatingActionButton: (@Composable () -> Unit)? = null,
  navigationIcon: @Composable (() -> Unit)? = null,
  trailingIcon: @Composable (() -> Unit)? = null,
  content: @Composable (PaddingValues) -> Unit,
) {
  val backgroundColor = if (isSystemInDarkTheme()) Color(0xFF0A0A0A) else MaterialTheme.colorScheme.background

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(backgroundColor)
  ) {
    Scaffold(
      containerColor = Color.Transparent,
      contentColor = MaterialTheme.colorScheme.onBackground,
      floatingActionButton = { floatingActionButton?.let { it() } },
      topBar = {
        CenterAlignedTopAppBar(
          title = topBarTitle,
          colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
          ),
          navigationIcon = {
            if (navigationIcon != null) {
              navigationIcon()
            }
          },
          actions = { trailingIcon?.let { it() } }
        )
      },
      content = { paddingValues ->
        content(paddingValues)
      }
    )
  }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun UpsScaffoldPreview() {
  UpsTheme {
    UpsScaffold(
      topBarTitle = { Text("Something") },
    ) {}
  }
}
