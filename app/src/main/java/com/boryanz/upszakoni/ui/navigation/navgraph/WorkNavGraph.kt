package com.boryanz.upszakoni.ui.navigation.navgraph

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.boryanz.upszakoni.data.local.database.model.owneditem.ItemCategory
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import com.boryanz.upszakoni.domain.remoteconfig.FirebaseRemoteConfig
import com.boryanz.upszakoni.data.model.TitleItem
import com.boryanz.upszakoni.ui.navigation.destinations.BonusSalaryDashboardDestination
import com.boryanz.upszakoni.ui.navigation.destinations.MigrationProposalDestination
import com.boryanz.upszakoni.ui.navigation.destinations.NewOvertimeInputDestination
import com.boryanz.upszakoni.ui.navigation.destinations.NonWorkingDaysInfoDestination
import com.boryanz.upszakoni.ui.navigation.destinations.OvertimeInputDestination
import com.boryanz.upszakoni.ui.navigation.destinations.OvertimeMonthlyCalendarDestination
import com.boryanz.upszakoni.ui.navigation.destinations.OwnedItemScreenDestination
import com.boryanz.upszakoni.ui.navigation.destinations.OwnedItemsListScreenDestination
import com.boryanz.upszakoni.ui.navigation.destinations.ParametersDestination
import com.boryanz.upszakoni.ui.navigation.destinations.WorkHomeDestination
import com.boryanz.upszakoni.ui.navigation.destinations.WorkNavGraph
import com.boryanz.upszakoni.ui.navigation.navgraph.overtimetracking.OvertimeTrackNavigationGraphViewModel
import com.boryanz.upszakoni.ui.owneditem.addowneditem.OwnedItemScreen
import com.boryanz.upszakoni.ui.owneditem.overview.OwnedItemsListScreen
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryDestination
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryScreen
import com.boryanz.upszakoni.ui.screens.ai.history.GeneratedDocumentOverviewScreen
import com.boryanz.upszakoni.ui.screens.ai.history.GeneratedDocumentOverviewScreenDestination
import com.boryanz.upszakoni.ui.screens.ai.addprompt.AddPromptScreen
import com.boryanz.upszakoni.ui.screens.ai.document.DocumentScreen
import com.boryanz.upszakoni.ui.screens.ai.navigation.AddPromptDestination
import com.boryanz.upszakoni.ui.screens.ai.navigation.DocumentDestination
import com.boryanz.upszakoni.ui.screens.ai.navigation.PromptInformationDestination
import com.boryanz.upszakoni.ui.screens.ai.information.PromptInformationScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.NonWorkingDaysInfoScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.monthly.OvertimeMonthlyCalendarScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.MigrationProposalScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.BonusSalaryOverTimeInputScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.daily.NewOvertimeInputScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersScreen
import com.boryanz.upszakoni.ui.screens.work.WorkHomeScreen
import org.koin.compose.koinInject
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.workNavGraph(
    navController: NavController,
) {
    navigation<WorkNavGraph>(startDestination = WorkHomeDestination) {

        composable<WorkHomeDestination> {
            val storage: SharedPrefsManager = koinInject()
            val remoteConfig: FirebaseRemoteConfig = koinInject()
            val viewModel = koinViewModel<OvertimeTrackNavigationGraphViewModel>()
            val hasTresholdSet by viewModel.hasTresholdSet.collectAsStateWithLifecycle()
            val remoteConfigState by remoteConfig.remoteConfigState.collectAsStateWithLifecycle()

            WorkHomeScreen(
                isAiAvailable = remoteConfigState.isAiGeneratorAvailable,
                onOvertimeClicked = {
                    val dest = when {
                        remoteConfigState.shouldBackportOvertimeTracking -> BonusSalaryDashboardDestination
                        storage.hasUserMigratedToNewOvertimeTracking() -> {
                            if (hasTresholdSet == true) BonusSalaryDashboardDestination
                            else ParametersDestination
                        }
                        else -> MigrationProposalDestination
                    }
                    navController.navigate(dest)
                },
                onEquipmentClicked = {
                    navController.navigate(OwnedItemsListScreenDestination)
                },
                onAiDocsClicked = {
                    navController.navigate(DocumentHistoryDestination)
                }
            )
        }

        // Migration / Bonus Salary screens
        composable<MigrationProposalDestination> {
            MigrationProposalScreen(
                onMigrationAccepted = {
                    navController.navigate(ParametersDestination) {
                        popUpTo<MigrationProposalDestination> { inclusive = true }
                    }
                },
                onMigrationCancelled = { startDestination ->
                    navController.navigate(startDestination) {
                        popUpTo<MigrationProposalDestination> { inclusive = true }
                    }
                }
            )
        }

        composable<ParametersDestination> {
            BonusSalaryParametersScreen(
                onParametersSaved = {
                    navController.navigate(BonusSalaryDashboardDestination) {
                        popUpTo<ParametersDestination> { inclusive = true }
                    }
                }
            )
        }

        composable<BonusSalaryDashboardDestination> {
            val storage: SharedPrefsManager = koinInject()
            val remoteConfig: FirebaseRemoteConfig = koinInject()
            val remoteConfigState by remoteConfig.remoteConfigState.collectAsStateWithLifecycle()
            val useNewOvertimeFlow = storage.hasUserMigratedToNewOvertimeTracking() &&
                    !remoteConfigState.shouldBackportOvertimeTracking

            BonusSalaryDashboardScreen(
                onBackClicked = { navController.navigateUp() },
                onEditClicked = { navController.navigate(ParametersDestination) },
                onMonthClicked = { month ->
                    if (useNewOvertimeFlow) {
                        navController.navigate(OvertimeMonthlyCalendarDestination(month))
                    } else {
                        navController.navigate(OvertimeInputDestination(month))
                    }
                },
                onNonWorkingDaysClicked = {
                    navController.navigate(NonWorkingDaysInfoDestination(it))
                }
            )
        }

        // Old overtime input (BonusSalary flow)
        composable<OvertimeInputDestination> {
            val month = it.toRoute<OvertimeInputDestination>().month
            BonusSalaryOverTimeInputScreen(
                month = month,
                onBackClicked = { navController.navigateUp() }
            )
        }

        // New overtime calendar (OverTimeTrack flow)
        composable<OvertimeMonthlyCalendarDestination> { backStackEntry ->
            val month = backStackEntry.toRoute<OvertimeMonthlyCalendarDestination>().monthName
            OvertimeMonthlyCalendarScreen(
                monthName = month,
                onDayInMonthClicked = { day ->
                    navController.navigate(
                        NewOvertimeInputDestination(
                            monthId = day.id,
                            monthName = month,
                            dayNumber = day.dayNumber,
                        )
                    )
                },
                onBackClicked = { navController.navigateUp() }
            )
        }

        composable<NewOvertimeInputDestination> {
            val route = it.toRoute<NewOvertimeInputDestination>()
            NewOvertimeInputScreen(
                monthId = route.monthId,
                monthName = route.monthName,
                dayNumber = route.dayNumber,
                onSaveClicked = { navController.navigateUp() },
                onBackClicked = { navController.navigateUp() },
            )
        }

        composable<NonWorkingDaysInfoDestination> {
            val nonWorkingDays = it.toRoute<NonWorkingDaysInfoDestination>().nonWorkingDays
            NonWorkingDaysInfoScreen(
                content = listOf(TitleItem(nonWorkingDays)),
                onBackClicked = navController::navigateUp
            )
        }

        // Owned Items screens
        composable<OwnedItemsListScreenDestination> {
            OwnedItemsListScreen(
                onBackClicked = { navController.navigateUp() },
                onItemClick = { item ->
                    navController.navigate(
                        OwnedItemScreenDestination(
                            itemId = item.id,
                            itemName = item.name,
                            volume = item.volume,
                            category = item.category
                        )
                    )
                },
                onAddItemClicked = {
                    navController.navigate(
                        OwnedItemScreenDestination(
                            itemId = 0,
                            itemName = "",
                            volume = 0,
                            category = ItemCategory.OTHER.name
                        )
                    )
                }
            )
        }

        composable<OwnedItemScreenDestination> {
            val route = it.toRoute<OwnedItemScreenDestination>()
            OwnedItemScreen(
                itemId = route.itemId,
                itemName = route.itemName,
                volume = route.volume,
                category = route.category,
                onBackClicked = { navController.navigateUp() }
            )
        }

        // AI Document screens
        composable<DocumentHistoryDestination> {
            DocumentHistoryScreen(
                onBackClicked = { navController.navigateUp() },
                onAddDocumentClicked = { navController.navigate(AddPromptDestination) },
                onDocumentClicked = {
                    navController.navigate(GeneratedDocumentOverviewScreenDestination(it))
                },
                onMoreInformationClicked = { navController.navigate(PromptInformationDestination) }
            )
        }

        composable<PromptInformationDestination> {
            PromptInformationScreen(onBackClicked = navController::navigateUp)
        }

        composable<GeneratedDocumentOverviewScreenDestination> {
            val route = it.toRoute<GeneratedDocumentOverviewScreenDestination>()
            GeneratedDocumentOverviewScreen(
                content = route.content,
                onBackClicked = navController::navigateUp,
            )
        }

        composable<AddPromptDestination> {
            AddPromptScreen(
                onBackClicked = { navController.navigateUp() },
                onGenerateDocumentClicked = { fullPrompt, examplePrompt, type ->
                    navController.navigate(
                        DocumentDestination(
                            fullPrompt = fullPrompt,
                            examplePrompt = examplePrompt,
                            type = type
                        )
                    )
                }
            )
        }

        composable<DocumentDestination> {
            val route = it.toRoute<DocumentDestination>()
            DocumentScreen(
                fullPrompt = route.fullPrompt,
                examplePrompt = route.examplePrompt,
                type = route.type,
                onBackClicked = { navController.navigateUp() }
            )
        }
    }
}
