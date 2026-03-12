package com.boryanz.upszakoni.ui.navigation.navgraph

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.analytics.AnalyticsLogger
import com.boryanz.upszakoni.customtab.CustomTabLauncher
import com.boryanz.upszakoni.data.goldenQuestions
import com.boryanz.upszakoni.data.policeAuthorities
import com.boryanz.upszakoni.domain.BaseError
import com.boryanz.upszakoni.ui.navigation.destinations.GoldenCrimeQuestionsDestination
import com.boryanz.upszakoni.ui.navigation.destinations.LawsDestination
import com.boryanz.upszakoni.ui.navigation.destinations.LawsNavGraph
import com.boryanz.upszakoni.ui.navigation.destinations.PoliceAuthoritiesDestination
import com.boryanz.upszakoni.ui.screens.crimequestions.GoldenCrimeQuestionsScreen
import com.boryanz.upszakoni.ui.screens.laws.LawsScreen
import com.boryanz.upszakoni.ui.screens.policeauthorities.PoliceAuthoritiesScreen
import com.boryanz.upszakoni.utils.openPdfWithExternalReader
import com.boryanz.upszakoni.utils.supportExternalPdfReader
import org.koin.compose.koinInject

fun NavGraphBuilder.lawsNavGraph(
    navController: NavController,
    context: Context,
    onShareAppClicked: () -> Unit,
    onAppUpdateClicked: () -> Unit,
    onError: (BaseError) -> Unit,
) {
    navigation<LawsNavGraph>(startDestination = LawsDestination) {
        composable<LawsDestination> {
            val analyticsLogger: AnalyticsLogger = koinInject()
            LawsScreen(
                onPdfReady = { lawName -> openPdfLaw(lawName, context) },
                onPoliceAuthoritiesClicked = { navController.navigate(PoliceAuthoritiesDestination) },
                onGoldenQuestionsClicked = { navController.navigate(GoldenCrimeQuestionsDestination) },
                onShareAppClicked = onShareAppClicked,
                onAppUpdateClicked = onAppUpdateClicked,
                onFeedbackFormClicked = {
                    CustomTabLauncher().launch(
                        context = context,
                        url = context.getString(R.string.feedback_form_url)
                    )
                },
                onError = onError,
            )
        }

        composable<PoliceAuthoritiesDestination> {
            PoliceAuthoritiesScreen(
                topBarTitle = context.getString(R.string.police_authorities_title),
                items = policeAuthorities,
                onBackClicked = { navController.navigateUp() }
            )
        }

        composable<GoldenCrimeQuestionsDestination> {
            val analyticsLogger: AnalyticsLogger = koinInject()
            GoldenCrimeQuestionsScreen(
                topBarTitle = context.getString(R.string.golden_questions_title),
                items = goldenQuestions,
                onBackClicked = { navController.navigateUp() },
                analyticsLogger = analyticsLogger
            )
        }
    }
}

private fun openPdfLaw(lawName: String, context: Context) {
    if (supportExternalPdfReader(context)) {
        openPdfWithExternalReader(context, lawName)
    }
}
