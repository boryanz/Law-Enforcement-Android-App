package com.boryanz.upszakoni.ui.navigation.navgraph

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import com.boryanz.upszakoni.ui.components.BottomNavigationBar
import com.boryanz.upszakoni.ui.components.BottomTab
import com.boryanz.upszakoni.ui.navigation.destinations.HomeNavGraph
import com.boryanz.upszakoni.ui.navigation.destinations.LawsNavGraph
import com.boryanz.upszakoni.ui.navigation.destinations.MoreNavGraph
import com.boryanz.upszakoni.ui.navigation.destinations.OffensesNavGraph
import com.boryanz.upszakoni.ui.navigation.destinations.PrivacyPolicyAcceptanceDestination
import com.boryanz.upszakoni.ui.navigation.destinations.WorkNavGraph
import com.boryanz.upszakoni.ui.screens.privacypolicy.PrivacyPolicyAcceptanceScreen
import com.boryanz.upszakoni.utils.noEnterTransition
import com.boryanz.upszakoni.utils.noExitTransition
import org.koin.compose.koinInject

@Composable
fun AppNavigationGraph(
    navController: NavHostController = rememberNavController(),
    onShareAppClicked: () -> Unit,
    onAppUpdateClicked: () -> Unit,
) {
    val context = LocalContext.current
    val storage: SharedPrefsManager = koinInject()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = currentDestination?.hasRoute<PrivacyPolicyAcceptanceDestination>() != true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    currentDestination = currentDestination,
                    onTabSelected = { tab ->
                        val graphRoute = when (tab) {
                            BottomTab.Home -> HomeNavGraph
                            BottomTab.Laws -> LawsNavGraph
                            BottomTab.Offenses -> OffensesNavGraph
                            BottomTab.Work -> WorkNavGraph
                            BottomTab.More -> MoreNavGraph
                        }
                        navController.navigate(graphRoute) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = if (storage.isPrivacyPolicyAccepted()) HomeNavGraph else PrivacyPolicyAcceptanceDestination,
            modifier = Modifier.padding(paddingValues),
            enterTransition = noEnterTransition,
            exitTransition = noExitTransition
        ) {
            composable<PrivacyPolicyAcceptanceDestination> {
                PrivacyPolicyAcceptanceScreen(
                    onContinueClicked = {
                        navController.navigate(HomeNavGraph) {
                            popUpTo<PrivacyPolicyAcceptanceDestination> { inclusive = true }
                        }
                    },
                    backButton = {}
                )
            }

            homeNavGraph(
                navController = navController,
                onSwitchToTab = { graphRoute ->
                    navController.navigate(graphRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            lawsNavGraph(
                navController = navController,
                context = context,
                onShareAppClicked = onShareAppClicked,
                onAppUpdateClicked = onAppUpdateClicked,
                onError = { it.handle(context) }
            )

            offensesNavGraph(
                navController = navController,
                context = context,
                onError = { it.handle(context) }
            )

            workNavGraph(
                navController = navController,
            )

            moreNavGraph(
                navController = navController,
                context = context,
                onShareAppClicked = onShareAppClicked,
                onAppUpdateClicked = onAppUpdateClicked,
            )
        }
    }
}
