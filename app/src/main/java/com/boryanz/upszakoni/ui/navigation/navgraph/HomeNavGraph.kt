package com.boryanz.upszakoni.ui.navigation.navgraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.boryanz.upszakoni.ui.navigation.destinations.GoldenCrimeQuestionsDestination
import com.boryanz.upszakoni.ui.navigation.destinations.HomeDestination
import com.boryanz.upszakoni.ui.navigation.destinations.HomeNavGraph
import com.boryanz.upszakoni.ui.navigation.destinations.LawsNavGraph
import com.boryanz.upszakoni.ui.navigation.destinations.MoreNavGraph
import com.boryanz.upszakoni.ui.navigation.destinations.OffensesNavGraph
import com.boryanz.upszakoni.ui.navigation.destinations.PhoneNumbersDestination
import com.boryanz.upszakoni.ui.navigation.destinations.WorkNavGraph
import com.boryanz.upszakoni.ui.screens.home.HomeScreen

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    onSwitchToTab: (Any) -> Unit,
) {
    navigation<HomeNavGraph>(startDestination = HomeDestination) {
        composable<HomeDestination> {
            HomeScreen(
                onLawsClicked = { onSwitchToTab(LawsNavGraph) },
                onOffensesClicked = { onSwitchToTab(OffensesNavGraph) },
                onOvertimeClicked = { onSwitchToTab(WorkNavGraph) },
                onCrimesClicked = { onSwitchToTab(OffensesNavGraph) },
                onGoldenQuestionsClicked = {
                    navController.navigate(GoldenCrimeQuestionsDestination)
                },
                onPhoneNumbersClicked = {
                    navController.navigate(PhoneNumbersDestination)
                },
            )
        }
    }
}
