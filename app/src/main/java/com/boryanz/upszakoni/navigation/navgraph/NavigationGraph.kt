package com.boryanz.upszakoni.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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
import com.boryanz.upszakoni.ui.screens.DashboardScreen
import com.boryanz.upszakoni.ui.screens.CommonCrimes
import com.boryanz.upszakoni.ui.screens.LawsScreen
import com.boryanz.upszakoni.ui.screens.PdfViewerActivity
import com.boryanz.upszakoni.ui.screens.PoliceAuthorityScreen

@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    NavHost(
        navController = navHostController,
        startDestination = Dashboard
    ) {
        composable<Dashboard> {
            DashboardScreen(
                onNavigateNext = { dashboardDestination ->
                    when (dashboardDestination) {
                        DashboardItemDestination.laws -> navHostController.navigate(Laws)
                        DashboardItemDestination.offenses -> navHostController.navigate(Offenses)
                        DashboardItemDestination.crimes -> navHostController.navigate(Crimes)
                        DashboardItemDestination.authorities -> navHostController.navigate(PoliceAuthorities)
                    }
                }
            )
        }
        composable<Offenses> {
            CommonCrimes(
                title = "Прекршоци",
                commonCrimesItems = offensesItems
            )
        }

        composable<Crimes> {
            CommonCrimes(
                title = "Кривични дела",
                commonCrimesItems = crimesItems
            )
        }

        composable<PoliceAuthorities> {
            PoliceAuthorityScreen()
        }

        composable<Laws> {
            LawsScreen(
                onClick = {
                    context.startActivity(PdfViewerActivity.createIntent(context, it))
                }
            )
        }
    }
}