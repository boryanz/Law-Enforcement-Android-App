package com.boryanz.upszakoni.ui.navigation.navgraph

import android.content.Context
import com.boryanz.upszakoni.domain.BaseError
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.boryanz.upszakoni.ui.navigation.destinations.CrimesDestination
import com.boryanz.upszakoni.ui.navigation.destinations.OffensesDetailsDestination
import com.boryanz.upszakoni.ui.navigation.destinations.OffensesNavGraph
import com.boryanz.upszakoni.ui.navigation.destinations.OffensesOverviewDestination
import com.boryanz.upszakoni.ui.screens.common.CommonCrimesScreen
import com.boryanz.upszakoni.ui.screens.common.OffensesDetailsScreen
import com.boryanz.upszakoni.ui.screens.common.OffensesOverviewScreen

fun NavGraphBuilder.offensesNavGraph(
    navController: NavController,
    context: Context,
    onError: (BaseError) -> Unit,
) {
    navigation<OffensesNavGraph>(startDestination = OffensesOverviewDestination) {
        composable<OffensesOverviewDestination> {
            OffensesOverviewScreen(
                onItemClicked = { law ->
                    navController.navigate(
                        OffensesDetailsDestination(
                            lawId = law.lawId,
                            title = law.title
                        )
                    )
                },
                onBackClicked = null
            )
        }

        composable<OffensesDetailsDestination> {
            val args = it.toRoute<OffensesDetailsDestination>()
            OffensesDetailsScreen(
                lawId = args.lawId,
                title = args.title,
                onBackClicked = { navController.navigateUp() },
                onFailure = { error -> onError(error) }
            )
        }

        composable<CrimesDestination> {
            CommonCrimesScreen(
                onBackClicked = { navController.navigateUp() }
            )
        }
    }
}
