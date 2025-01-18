package com.boryanz.upszakoni.ui.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boryanz.upszakoni.ui.navigation.destinations.DashboardDestination
import com.boryanz.upszakoni.ui.navigation.destinations.OvertimeInputDestination
import com.boryanz.upszakoni.ui.navigation.destinations.ParametersDestination


@Composable
fun BonusSalaryNavigationGraph(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = ParametersDestination
    ) {
        composable<ParametersDestination> {

        }

        composable<DashboardDestination> {

        }

        composable<OvertimeInputDestination> {

        }
    }
}