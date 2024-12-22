package com.boryanz.upszakoni.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.data.NavigationDrawerDestination
import com.boryanz.upszakoni.ui.theme.Base100
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    screenTitle: String,
    onItemClicked: (NavigationDrawerDestination) -> Unit,
    content: @Composable (PaddingValues) -> Unit,
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
                        Text("УПС", modifier = Modifier.padding(16.dp))
                        HorizontalDivider()
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(20.dp),
                                    painter = painterResource(R.drawable.offenses),
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = "Чести прекршоци") },
                            selected = false,
                            onClick = { onItemClicked(NavigationDrawerDestination.offenses) }
                        )
                        HorizontalDivider()
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(20.dp),
                                    painter = painterResource(R.drawable.kriminal),
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = "Чести кривични дела") },
                            selected = false,
                            onClick = { onItemClicked(NavigationDrawerDestination.crimes) }
                        )
                        HorizontalDivider()
                        Spacer.Vertical(14.dp)
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(20.dp),
                                    painter = painterResource(R.drawable.police),
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = "Твои овластувања") },
                            selected = false,
                            onClick = { onItemClicked(NavigationDrawerDestination.authorities) }
                        )
                        HorizontalDivider()
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(20.dp),
                                    painter = painterResource(R.drawable.question_solid),
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = "Прашања за службена белешка") },
                            selected = false,
                            onClick = { onItemClicked(NavigationDrawerDestination.writing_guide) }
                        )
                        HorizontalDivider()
                        Spacer.Vertical(14.dp)
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(20.dp),
                                    painter = painterResource(R.drawable.prekrsok),
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = "Потраги и исчезнати лица") },
                            selected = false,
                            onClick = { onItemClicked(NavigationDrawerDestination.wanted_criminals) }
                        )
                        HorizontalDivider()
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(20.dp),
                                    painter = painterResource(R.drawable.vesti),
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = "Дневен билтен") },
                            selected = false,
                            onClick = { onItemClicked(NavigationDrawerDestination.daily_news) }
                        )
                        HorizontalDivider()
                        Spacer.Vertical(14.dp)
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(20.dp),
                                    painter = painterResource(R.drawable.phone_solid),
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = "Броеви од ПС - СВР Скопје") },
                            selected = false,
                            onClick = { onItemClicked(NavigationDrawerDestination.phone_numbers) }
                        )
                        Spacer.Vertical(24.dp)
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(20.dp),
                                    painter = painterResource(R.drawable.vesti),
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = "Политика за приватност") },
                            selected = false,
                            onClick = { onItemClicked(NavigationDrawerDestination.privacy_policy) }
                        )
                    }
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(start = 12.dp),
                        text = "Имаш нов пречистен текст или забелешки? Испрати на boryans.co@gmail.com",
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        UpsScaffold(
            topBarTitle = { Text(screenTitle) },
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        } else {
                            drawerState.close()
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        tint = Base100,
                        contentDescription = "Menu"
                    )
                }
            }

        ) { paddingValues ->
            content(paddingValues)
        }
    }
}