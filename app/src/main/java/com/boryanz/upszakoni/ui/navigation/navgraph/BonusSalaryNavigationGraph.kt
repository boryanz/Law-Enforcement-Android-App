package com.boryanz.upszakoni.ui.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boryanz.upszakoni.ui.navigation.destinations.BonusSalaryDashboardDestination
import com.boryanz.upszakoni.ui.navigation.destinations.OvertimeInputDestination
import com.boryanz.upszakoni.ui.navigation.destinations.ParametersDestination
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersScreen


@Composable
fun BonusSalaryNavigationGraph(
    navHostController: NavHostController = rememberNavController(),
    onFinishActivity: () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = ParametersDestination,
    ) {
        composable<ParametersDestination> {
            BonusSalaryParametersScreen(
                navController = navHostController,
                onBackClicked = onFinishActivity
            )
        }

        composable<BonusSalaryDashboardDestination> {

        }

        composable<OvertimeInputDestination> {

        }
    }
}