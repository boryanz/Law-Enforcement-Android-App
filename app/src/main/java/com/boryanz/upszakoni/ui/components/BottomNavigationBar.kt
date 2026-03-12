package com.boryanz.upszakoni.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.navigation.destinations.HomeNavGraph
import com.boryanz.upszakoni.ui.navigation.destinations.LawsNavGraph
import com.boryanz.upszakoni.ui.navigation.destinations.MoreNavGraph
import com.boryanz.upszakoni.ui.navigation.destinations.OffensesNavGraph
import com.boryanz.upszakoni.ui.navigation.destinations.WorkNavGraph
import kotlin.reflect.KClass

sealed class BottomTab(val graphRouteClass: KClass<*>, val label: String) {
    data object Home : BottomTab(HomeNavGraph::class, "Дома")
    data object Laws : BottomTab(LawsNavGraph::class, "Закони")
    data object Offenses : BottomTab(OffensesNavGraph::class, "Прекршоци")
    data object Work : BottomTab(WorkNavGraph::class, "Работа")
    data object More : BottomTab(MoreNavGraph::class, "Повеќе")
}

@Composable
fun BottomNavigationBar(
    currentDestination: NavDestination?,
    onTabSelected: (BottomTab) -> Unit,
) {
    val tabs = listOf(
        BottomTab.Home,
        BottomTab.Laws,
        BottomTab.Offenses,
        BottomTab.Work,
        BottomTab.More
    )

    NavigationBar {
        tabs.forEach { tab ->
            val selected = currentDestination?.hierarchy?.any { dest ->
                dest.hasRoute(tab.graphRouteClass)
            } == true

            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(tab) },
                icon = {
                    when (tab) {
                        BottomTab.Home -> Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = tab.label
                        )
                        BottomTab.Laws -> Icon(
                            imageVector = Icons.Filled.MenuBook,
                            contentDescription = tab.label
                        )
                        BottomTab.Offenses -> Icon(
                            painter = painterResource(R.drawable.offenses),
                            contentDescription = tab.label
                        )
                        BottomTab.Work -> Icon(
                            imageVector = Icons.Filled.Timelapse,
                            contentDescription = tab.label
                        )
                        BottomTab.More -> Icon(
                            imageVector = Icons.Filled.MoreHoriz,
                            contentDescription = tab.label
                        )
                    }
                },
                label = { Text(tab.label) }
            )
        }
    }
}
