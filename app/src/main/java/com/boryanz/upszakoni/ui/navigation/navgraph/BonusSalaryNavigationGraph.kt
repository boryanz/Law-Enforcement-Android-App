package com.boryanz.upszakoni.ui.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.boryanz.upszakoni.ui.screens.bonussalary.BonusSalaryViewModel
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.NonWorkingDaysInfoScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.MigrationProposalScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.BonusSalaryOverTimeInputScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun BonusSalaryNavigationGraph(
    navHostController: NavHostController = rememberNavController(),
    onBackNavigated: () -> Unit,
    onMigrationAccepted: () -> Unit,
) {

    val viewModel = koinViewModel<BonusSalaryViewModel>()
    val hasParametersSet by viewModel.uiState.collectAsStateWithLifecycle()
    if (hasParametersSet == null) return

    val startDestination: Any =
        if (hasParametersSet == true) BonusSalaryDashboardDestination else ParametersDestination

    NavHost(
        navController = navHostController,
        startDestination = MigrationProposalDestination,
    ) {

        composable<MigrationProposalDestination> {
            MigrationProposalScreen(
                onMigrationAccepted = onMigrationAccepted,
                onMigrationCancelled = {}
            )
        }
        composable<ParametersDestination> {
            BonusSalaryParametersScreen(
                navController = navHostController,
                onBackClicked = onBackNavigated
            )
        }

        composable<BonusSalaryDashboardDestination> {
            BonusSalaryDashboardScreen(
                navHostController = navHostController,
                onBackClicked = onBackNavigated,
                onEditClicked = { navHostController.navigate(ParametersDestination) }
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