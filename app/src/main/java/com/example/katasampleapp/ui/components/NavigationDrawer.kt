package com.example.katasampleapp.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.katasampleapp.ui.theme.KataSampleAppTheme
import kotlinx.coroutines.launch

object NavigationDrawer {

    @Composable
    fun WithFooter(
        content: @Composable () -> Unit,
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        BackHandler(enabled = drawerState.isOpen, onBack = {
            scope.launch {
                drawerState.close()
            }
        })

        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column {
                            Spacer.Vertical(12.dp)
                            Text("Title", modifier = Modifier.padding(16.dp))
                            HorizontalDivider()
                            NavigationDrawerItem(
                                label = { Text("First item") },
                                onClick = {},
                                selected = true
                            )
                            HorizontalDivider()
                            NavigationDrawerItem(
                                label = { Text("Second item") },
                                onClick = {},
                                selected = true
                            )

                        }
                        HorizontalDivider()
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            text = "Footer area",
                            textAlign = TextAlign.Start,
                            fontSize = 14.sp
                        )
                        Spacer.Vertical(8.dp)
                    }
                }
            },
            drawerState = drawerState
        ) {
            content()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun NavigationDrawerPreview() {
    KataSampleAppTheme {
        NavigationDrawer.WithFooter {
            Scaffold.WithTopBar(
                topBar = {
                    TopAppBar(title = { Texts.Title("Title") })
                },
                fab = {},
                content = {}
            )
        }
    }
}