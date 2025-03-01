package com.boryanz.upszakoni.ui.navigation.navgraph.overtimetracking

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.boryanz.upszakoni.data.model.TitleItem
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.domain.errorhandling.fold
import com.boryanz.upszakoni.ui.navigation.destinations.BonusSalaryDashboardDestination
import com.boryanz.upszakoni.ui.navigation.destinations.NewOvertimeInputDestination
import com.boryanz.upszakoni.ui.navigation.destinations.NonWorkingDaysInfoDestination
import com.boryanz.upszakoni.ui.navigation.destinations.OvertimeMonthlyCalendarDestination
import com.boryanz.upszakoni.ui.navigation.destinations.ParametersDestination
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.NonWorkingDaysInfoScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.monthly.OvertimeMonthlyCalendarScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.NewOvertimeInputScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersScreen
import com.boryanz.upszakoni.utils.noEnterTransition
import com.boryanz.upszakoni.utils.noExitTransition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class OverTimeTrackNavigationGraphViewModel(
    private val bonusSalaryRepository: BonusSalaryRepository
) : ViewModel() {

    private val _hasTresholdSet: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val hasTresholdSet = _hasTresholdSet.asStateFlow()

    init {
        checkIfUserAlreadyHaveData()
    }

    private fun checkIfUserAlreadyHaveData() = viewModelScope.launch(Dispatchers.IO) {
        bonusSalaryRepository.getTreshold("bonus_salary_treshold").fold(
            onSuccess = { _hasTresholdSet.emit(it != null) },
            onFailure = { _hasTresholdSet.emit(false) }
        )
    }
}


@Composable
fun OverTimeTrackNavigationGraph(
    navHostController: NavHostController = rememberNavController(),
    onBackNavigated: () -> Unit,
) {
    val viewModel = koinViewModel<OverTimeTrackNavigationGraphViewModel>()
    val hasTresholdSet by viewModel.hasTresholdSet.collectAsStateWithLifecycle()
    if (hasTresholdSet == null) return

    val startDestination: Any = if (hasTresholdSet == true) {
        BonusSalaryDashboardDestination
    } else {
        ParametersDestination
    }

    NavHost(
        startDestination = startDestination,
        navController = navHostController,
        enterTransition = noEnterTransition,
        exitTransition = noExitTransition
    ) {

        composable<ParametersDestination> {
            BonusSalaryParametersScreen(
                navController = navHostController,
            )
        }

        composable<BonusSalaryDashboardDestination> {
            BonusSalaryDashboardScreen(
                onBackClicked = onBackNavigated,
                onEditClicked = { navHostController.navigate(ParametersDestination) },
                onMonthClicked = { navHostController.navigate(OvertimeMonthlyCalendarDestination(it)) },
                onNonWorkingDaysClicked = {
                    navHostController.navigate(
                        NonWorkingDaysInfoDestination(
                            it
                        )
                    )
                }
            )
        }

        composable<OvertimeMonthlyCalendarDestination> {
            val month = it.toRoute<OvertimeMonthlyCalendarDestination>().monthId
            OvertimeMonthlyCalendarScreen(
                navHostController = navHostController,
                monthId = month,
                onDayInMonthClicked = {
                    navHostController.navigate(
                        NewOvertimeInputDestination(
                            "",
                            ""
                        )
                    )
                },
                onBackClicked = { navHostController.navigateUp() }
            )
        }

        composable<NewOvertimeInputDestination> {
            val month = it.toRoute<NewOvertimeInputDestination>().monthId
            val dayOfTheMonth = it.toRoute<NewOvertimeInputDestination>().dayOfTheMonthId
            NewOvertimeInputScreen(
                monthId = month,
                dayOfTheMonthId = dayOfTheMonth,
                onSaveClicked = { navHostController.navigateUp() },
                onBackClicked = { navHostController.navigateUp() }
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