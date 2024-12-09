package com.boryanz.upszakoni.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boryanz.upszakoni.navigation.destinations.Screen1
import com.boryanz.upszakoni.navigation.destinations.Screen2
import com.boryanz.upszakoni.ui.screens.Screen1
import com.boryanz.upszakoni.ui.screens.Screen2

@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen1
    ) {
        composable<Screen1> {
            Screen1(
                onNavigateNext = {
                    navHostController.navigate(Screen2)
                }
            )
        }
        composable<Screen2> {
            Screen2(
                onNavigateBack = { navHostController.navigateUp() }
            )
        }
    }
}