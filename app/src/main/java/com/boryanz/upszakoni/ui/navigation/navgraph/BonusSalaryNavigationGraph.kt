package com.boryanz.upszakoni.ui.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.boryanz.upszakoni.data.model.TitleItem
import com.boryanz.upszakoni.ui.navigation.destinations.BonusSalaryDashboardDestination
import com.boryanz.upszakoni.ui.navigation.destinations.MigrationProposalDestination
import com.boryanz.upszakoni.ui.navigation.destinations.NonWorkingDaysInfoDestination
import com.boryanz.upszakoni.ui.navigation.destinations.OvertimeInputDestination
import com.boryanz.upszakoni.ui.navigation.destinations.ParametersDestination
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.NonWorkingDaysInfoScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.MigrationProposalScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.BonusSalaryOverTimeInputScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersScreen
import com.boryanz.upszakoni.utils.noEnterTransition
import com.boryanz.upszakoni.utils.noExitTransition


@Composable
fun BonusSalaryNavigationGraph(
    shouldBackportOvertimeTracking: Boolean = false,
    navHostController: NavHostController = rememberNavController(),
    onBackNavigated: () -> Unit,
    onMigrationAccepted: () -> Unit,
) {

    NavHost(
        navController = navHostController,
        startDestination = if (shouldBackportOvertimeTracking) BonusSalaryDashboardDestination else MigrationProposalDestination,
        enterTransition = noEnterTransition,
        exitTransition = noExitTransition
    ) {

        composable<MigrationProposalDestination> {
            MigrationProposalScreen(
                onMigrationAccepted = onMigrationAccepted,
                onMigrationCancelled = { navHostController.navigate(it) }
            )
        }
        composable<ParametersDestination> {
            BonusSalaryParametersScreen(
                navController = navHostController,
            )
        }

        composable<BonusSalaryDashboardDestination> {
            BonusSalaryDashboardScreen(
                onBackClicked = onBackNavigated,
                onEditClicked = { navHostController.navigate(ParametersDestination) },
                onMonthClicked = { navHostController.navigate(OvertimeInputDestination(it)) },
                onNonWorkingDaysClicked = {
                    navHostController.navigate(
                        NonWorkingDaysInfoDestination(
                            it
                        )
                    )
                }
            )
        }

        composable<OvertimeInputDestination> {
            val month = it.toRoute<OvertimeInputDestination>().month
            BonusSalaryOverTimeInputScreen(month = month, navHostController = navHostController)
        }

        composable<NonWorkingDaysInfoDestination> {
            val nonWorkingDays = it.toRoute<NonWorkingDaysInfoDestination>().nonWorkingDays
            NonWorkingDaysInfoScreen(
                content = listOf(TitleItem(nonWorkingDays)),
                onBackClicked = navHostController::navigateUp
            )
        }
    }
}