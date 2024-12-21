package com.boryanz.upszakoni.navigation.navgraph

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boryanz.upszakoni.data.DashboardItemDestination
import com.boryanz.upszakoni.data.crimesItems
import com.boryanz.upszakoni.data.goldenQuestions
import com.boryanz.upszakoni.data.offensesItems
import com.boryanz.upszakoni.data.policeAuthorities
import com.boryanz.upszakoni.navigation.destinations.Crimes
import com.boryanz.upszakoni.navigation.destinations.Dashboard
import com.boryanz.upszakoni.navigation.destinations.GoldenCrimeQuestions
import com.boryanz.upszakoni.navigation.destinations.Laws
import com.boryanz.upszakoni.navigation.destinations.Offenses
import com.boryanz.upszakoni.navigation.destinations.PoliceAuthorities
import com.boryanz.upszakoni.ui.screens.DashboardScreen
import com.boryanz.upszakoni.ui.screens.CommonCrimes
import com.boryanz.upszakoni.ui.screens.LawsScreen
import com.boryanz.upszakoni.ui.screens.PdfViewerActivity
import com.boryanz.upszakoni.ui.screens.GoldenCrimeQuestionsScreen
import com.boryanz.upszakoni.ui.screens.PoliceAuthoritiesScreen
import com.boryanz.upszakoni.utils.supportExternalPdfReader
import com.boryanz.upszakoni.utils.openPdfWithExternalReader

@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    val isInDarkMode = isSystemInDarkTheme()
    NavHost(
        navController = navHostController,
        startDestination = Dashboard
    ) {
        composable<Dashboard> {
            DashboardScreen(
                onNavigateNext = { dashboardDestination ->
                    when (dashboardDestination) {
                        DashboardItemDestination.laws -> navHostController.navigate(Laws)
                        DashboardItemDestination.offenses -> navHostController.navigate(Offenses)
                        DashboardItemDestination.crimes -> navHostController.navigate(Crimes)
                        DashboardItemDestination.authorities -> navHostController.navigate(
                            PoliceAuthorities
                        )

                        DashboardItemDestination.wanted_criminals -> {
                            val customTabIntent = CustomTabsIntent.Builder().apply {
                                setShowTitle(true)
                            }.build()
                            customTabIntent.launchUrl(
                                context,
                                Uri.parse("https://mvr.gov.mk/potragi-ischeznati/potragi")
                            )
                        }

                        DashboardItemDestination.writing_guide -> {
                            navHostController.navigate(GoldenCrimeQuestions)
                        }
                    }
                }
            )
        }
        composable<Offenses> {
            CommonCrimes(
                title = "Прекршоци",
                commonCrimesItems = offensesItems,
                onClick = { lawName, pagesToLoad ->
                    val bundle = bundleOf(
                        PdfViewerActivity.BUNDLE_LAW_TITLE to lawName,
                        PdfViewerActivity.BUNDLE_PAGES_TO_LOAD to pagesToLoad.toIntArray(),
                        PdfViewerActivity.BUNDLE_IS_DARK_MODE to isInDarkMode
                    )
                    context.startActivity(PdfViewerActivity.createIntent(context, bundle))
                }
            )
        }

        composable<Crimes> {
            CommonCrimes(
                title = "Кривични дела",
                commonCrimesItems = crimesItems,
                onClick = { lawName, pagesToLoad ->
                    val bundle = bundleOf(
                        PdfViewerActivity.BUNDLE_LAW_TITLE to lawName,
                        PdfViewerActivity.BUNDLE_PAGES_TO_LOAD to pagesToLoad.toIntArray(),
                        PdfViewerActivity.BUNDLE_IS_DARK_MODE to isInDarkMode
                    )
                    context.startActivity(PdfViewerActivity.createIntent(context, bundle))
                }
            )
        }

        composable<PoliceAuthorities> {
            PoliceAuthoritiesScreen("Полициски овластувања", policeAuthorities)
        }

        composable<GoldenCrimeQuestions> {
            GoldenCrimeQuestionsScreen("Водич за службена белешка", goldenQuestions)
        }

        composable<Laws> {
            LawsScreen(
                onClick = { lawName ->
                    val bundle = bundleOf(
                        PdfViewerActivity.BUNDLE_LAW_TITLE to lawName,
                        PdfViewerActivity.BUNDLE_IS_DARK_MODE to isInDarkMode
                    )
                    if (supportExternalPdfReader(context)) {
                        openPdfWithExternalReader(context, lawName)
                    } else {
                        context.startActivity(PdfViewerActivity.createIntent(context, bundle))
                    }
                }
            )
        }
    }
}