package com.boryanz.upszakoni.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boryanz.upszakoni.data.DashboardItemDestination
import com.boryanz.upszakoni.data.crimesItems
import com.boryanz.upszakoni.data.offensesItems
import com.boryanz.upszakoni.navigation.destinations.Crimes
import com.boryanz.upszakoni.navigation.destinations.Dashboard
import com.boryanz.upszakoni.navigation.destinations.Laws
import com.boryanz.upszakoni.navigation.destinations.Offenses
import com.boryanz.upszakoni.navigation.destinations.PoliceAuthorities
import com.boryanz.upszakoni.ui.screens.Dashboard
import com.boryanz.upszakoni.ui.screens.CommonCrimes

@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navHostController,
        startDestination = Dashboard
    ) {
        composable<Dashboard> {
            Dashboard(
                onNavigateNext = { dashboardDestination ->
                    val destination = when (dashboardDestination) {
                        DashboardItemDestination.laws -> Laws
                        DashboardItemDestination.offenses -> Offenses
                        DashboardItemDestination.crimes -> Crimes
                        DashboardItemDestination.authorities -> PoliceAuthorities
                    }
                    navHostController.navigate(destination)
                }
            )
        }
        composable<Offenses> {
            CommonCrimes(
                title = "Најчести прекршоци",
                commonCrimesItems = offensesItems
            )
        }

        composable<Crimes> {
            CommonCrimes(
                title = "Најчести кривични дела",
                commonCrimesItems = crimesItems
            )
        }
    }
}