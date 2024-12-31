package com.example.katasampleapp.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.katasampleapp.ui.theme.KataSampleAppTheme

object Scaffold {

    @Composable
    fun WithFullNavigation(
        modifier: Modifier = Modifier,
        topBar: @Composable () -> Unit,
        bottomBar: @Composable () -> Unit,
        fab: @Composable () -> Unit,
        content: @Composable (PaddingValues) -> Unit
    ) {
        BaseScaffold(
            modifier = modifier,
            topBar = topBar,
            bottomBar = bottomBar,
            floatingActionButton = fab,
            content = content
        )
    }

    @Composable
    fun WithTopBar(
        modifier: Modifier = Modifier,
        topBar: @Composable () -> Unit,
        fab: @Composable () -> Unit,
        content: @Composable (PaddingValues) -> Unit
    ) {
        BaseScaffold(
            modifier = modifier,
            topBar = topBar,
            floatingActionButton = fab,
            content = content
        )
    }

    @Composable
    fun WithNavigationDrawer(
        modifier: Modifier = Modifier,
        topBar: @Composable () -> Unit,
        fab: @Composable () -> Unit,
        content: @Composable (PaddingValues) -> Unit
    ) {
        NavigationDrawer.WithFooter {
            BaseScaffold(
                modifier = modifier,
                topBar = topBar,
                floatingActionButton = fab,
                content = content
            )
        }
    }

    @Composable
    fun WithoutTopBar(
        modifier: Modifier = Modifier,
        fab: @Composable () -> Unit,
        content: @Composable (PaddingValues) -> Unit
    ) {
        BaseScaffold(
            modifier = modifier,
            topBar = {},
            floatingActionButton = fab,
            content = content
        )
    }


    @Composable
    fun BaseScaffold(
        modifier: Modifier = Modifier,
        topBar: @Composable () -> Unit = {},
        bottomBar: @Composable () -> Unit = {},
        snackbarHost: @Composable () -> Unit = {},
        floatingActionButton: @Composable () -> Unit = {},
        floatingActionButtonPosition: androidx.compose.material3.FabPosition = androidx.compose.material3.FabPosition.End,
        containerColor: Color = androidx.compose.material3.MaterialTheme.colorScheme.background,
        contentColor: Color = androidx.compose.material3.contentColorFor(containerColor),
        contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
        content: @Composable (PaddingValues) -> Unit
    ) {
        Scaffold(
            contentWindowInsets = contentWindowInsets,
            modifier = modifier,
            topBar = topBar,
            bottomBar = bottomBar,
            snackbarHost = snackbarHost,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            contentColor = contentColor,
            content = content
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ScaffoldPreview() {
    KataSampleAppTheme {

    }
}