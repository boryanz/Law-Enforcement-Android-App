package com.boryanz.upszakoni.ui.navigation.navgraph.overtimetracking

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.boryanz.upszakoni.data.NavigationDrawerDestination
import com.boryanz.upszakoni.data.model.TitleItem
import com.boryanz.upszakoni.ui.navigation.destinations.BonusSalaryDashboardDestination
import com.boryanz.upszakoni.ui.navigation.destinations.NewOvertimeInputDestination
import com.boryanz.upszakoni.ui.navigation.destinations.NonWorkingDaysInfoDestination
import com.boryanz.upszakoni.ui.navigation.destinations.OvertimeMonthlyCalendarDestination
import com.boryanz.upszakoni.ui.screens.ai.GenerateDocumentActivity
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.NonWorkingDaysInfoScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.monthly.OvertimeMonthlyCalendarScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.daily.NewOvertimeInputScreen
import com.boryanz.upszakoni.utils.noEnterTransition
import com.boryanz.upszakoni.utils.noExitTransition

@Composable
fun OverTimeTrackNavigationGraph(
  navHostController: NavHostController = rememberNavController(),
  onBackNavigated: () -> Unit,
) {
  val context = LocalContext.current

  val drawerHandler: (NavigationDrawerDestination) -> Unit = { destination ->
    when (destination) {
      NavigationDrawerDestination.generate_document ->
        context.startActivity(GenerateDocumentActivity.createIntent(context))
      else -> onBackNavigated()
    }
  }

  NavHost(
    startDestination = BonusSalaryDashboardDestination,
    navController = navHostController,
    enterTransition = noEnterTransition,
    exitTransition = noExitTransition
  ) {

    composable<BonusSalaryDashboardDestination> {
      BonusSalaryDashboardScreen(
        onBackClicked = onBackNavigated,
        onMonthClicked = { navHostController.navigate(OvertimeMonthlyCalendarDestination(it)) },
        onNonWorkingDaysClicked = {
          navHostController.navigate(NonWorkingDaysInfoDestination(it))
        },
        onDrawerItemClicked = drawerHandler,
      )
    }

    composable<OvertimeMonthlyCalendarDestination> { backStackEntry ->
      val month = backStackEntry.toRoute<OvertimeMonthlyCalendarDestination>().monthName
      OvertimeMonthlyCalendarScreen(
        monthName = month,
        onDayInMonthClicked = { day ->
          navHostController.navigate(
            NewOvertimeInputDestination(
              monthId = day.id,
              monthName = month,
              dayNumber = day.dayNumber,
            )
          )
        },
        onBackClicked = { navHostController.navigateUp() }
      )
    }

    composable<NewOvertimeInputDestination> {
      val route = it.toRoute<NewOvertimeInputDestination>()
      NewOvertimeInputScreen(
        monthId = route.monthId,
        monthName = route.monthName,
        dayNumber = route.dayNumber,
        onSaveClicked = { navHostController.navigateUp() },
        onBackClicked = { navHostController.navigateUp() },
      )
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
