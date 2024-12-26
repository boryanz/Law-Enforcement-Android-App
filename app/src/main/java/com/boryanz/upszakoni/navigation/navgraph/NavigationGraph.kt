package com.boryanz.upszakoni.navigation.navgraph

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boryanz.upszakoni.customtab.CustomTabLauncher
import com.boryanz.upszakoni.data.NavigationDrawerDestination
import com.boryanz.upszakoni.data.crimesItems
import com.boryanz.upszakoni.data.goldenQuestions
import com.boryanz.upszakoni.data.offensesItems
import com.boryanz.upszakoni.data.policeAuthorities
import com.boryanz.upszakoni.navigation.destinations.Crimes
import com.boryanz.upszakoni.navigation.destinations.GoldenCrimeQuestions
import com.boryanz.upszakoni.navigation.destinations.Laws
import com.boryanz.upszakoni.navigation.destinations.Offenses
import com.boryanz.upszakoni.navigation.destinations.PhoneNumbers
import com.boryanz.upszakoni.navigation.destinations.PoliceAuthorities
import com.boryanz.upszakoni.navigation.destinations.PrivacyPolicy
import com.boryanz.upszakoni.navigation.destinations.PrivacyPolicyAcceptance
import com.boryanz.upszakoni.storage.sharedprefs.SharedPrefsDao
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.screens.CommonOffensesAndCrimes
import com.boryanz.upszakoni.ui.screens.LawsScreen
import com.boryanz.upszakoni.ui.screens.PdfViewerActivity
import com.boryanz.upszakoni.ui.screens.GoldenCrimeQuestionsScreen
import com.boryanz.upszakoni.ui.screens.PhoneNumbersScreen
import com.boryanz.upszakoni.ui.screens.PoliceAuthoritiesScreen
import com.boryanz.upszakoni.ui.screens.PrivacyPolicyAcceptanceScreen
import com.boryanz.upszakoni.ui.screens.PrivacyPolicyScreen
import com.boryanz.upszakoni.utils.openDialer
import com.boryanz.upszakoni.utils.supportExternalPdfReader
import com.boryanz.upszakoni.utils.openPdfWithExternalReader

private const val WANTED_PERSONS_URL = "https://mvr.gov.mk/potragi-ischeznati/potragi"
private const val DAILY_NEWS_URL = "https://mvr.gov.mk/dnevni-bilteni"

@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    val sharedPrefsDao = remember { SharedPrefsDao(context) }
    val isInDarkMode = isSystemInDarkTheme()
    NavHost(
        navController = navHostController,
        startDestination = if (sharedPrefsDao.isPrivacyPolicyAccepted()) Laws else PrivacyPolicyAcceptance
    ) {

        composable<PrivacyPolicyAcceptance> {
            PrivacyPolicyAcceptanceScreen(
                onContinueClicked = { navHostController.navigate(Laws) },
                backButton = {}
            )
        }

        composable<Offenses> {
            CommonOffensesAndCrimes(
                title = "Прекршоци",
                commonCrimesItems = offensesItems,
                onCrimeClicked = { lawName, pagesToLoad ->
                    context.navigateToInternalPdfViewer(
                        lawName = lawName,
                        pagesToLoad = pagesToLoad,
                        isInDarkMode = isInDarkMode
                    )
                },
                onBackClicked = { navHostController.navigateUp() }
            )
        }

        composable<Crimes> {
            CommonOffensesAndCrimes(
                title = "Кривични дела",
                commonCrimesItems = crimesItems,
                onCrimeClicked = { lawName, pagesToLoad ->
                    context.navigateToInternalPdfViewer(
                        lawName = lawName,
                        pagesToLoad = pagesToLoad,
                        isInDarkMode = isInDarkMode
                    )
                },
                onBackClicked = { navHostController.navigateUp() }
            )
        }

        composable<PoliceAuthorities> {
            PoliceAuthoritiesScreen(
                topBarTitle = "Полициски овластувања",
                items = policeAuthorities,
                onBackClicked = { navHostController.navigateUp() }
            )
        }

        composable<GoldenCrimeQuestions> {
            GoldenCrimeQuestionsScreen(
                topBarTitle = "Водич за службена белешка",
                items = goldenQuestions,
                onBackClicked = { navHostController.navigateUp() }
            )
        }

        composable<PhoneNumbers> {
            PhoneNumbersScreen(
                onContactClicked = { phoneNumber ->
                    context.openDialer(phoneNumber)
                },
                onBackClicked = { navHostController.navigateUp() }
            )
        }

        composable<PrivacyPolicy> {
            PrivacyPolicyScreen(
                backButton = {
                    Icons.Back(onClick = { navHostController.navigateUp() })
                }
            )
        }

        composable<Laws> {
            LawsScreen(
                onItemClick = { navigationDrawerDestination ->
                    navHostController.navigateToDrawerDestination(navigationDrawerDestination)
                },
                onLawClick = { lawName ->
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

fun NavHostController.navigateToDrawerDestination(navigationDrawerDestination: NavigationDrawerDestination) {
    when (navigationDrawerDestination) {
        NavigationDrawerDestination.laws -> navigate(Laws)
        NavigationDrawerDestination.offenses -> navigate(Offenses)
        NavigationDrawerDestination.crimes -> navigate(Crimes)
        NavigationDrawerDestination.authorities -> navigate(PoliceAuthorities)
        NavigationDrawerDestination.writing_guide -> navigate(GoldenCrimeQuestions)
        NavigationDrawerDestination.wanted_criminals -> {
            val customTabLauncher = CustomTabLauncher(
                showTitle = true,
                setUrlBarHiddenEnabled = true
            )
            customTabLauncher.launch(context, WANTED_PERSONS_URL)
        }

        NavigationDrawerDestination.daily_news -> {
            val customTabLauncher = CustomTabLauncher(
                showTitle = true,
                setUrlBarHiddenEnabled = true
            )
            customTabLauncher.launch(context, DAILY_NEWS_URL)
        }

        NavigationDrawerDestination.phone_numbers -> navigate(PhoneNumbers)
        NavigationDrawerDestination.privacy_policy -> navigate(PrivacyPolicy)
    }
}

fun Context.navigateToInternalPdfViewer(
    lawName: String,
    pagesToLoad: List<Int>,
    isInDarkMode: Boolean
) {
    val bundle = bundleOf(
        PdfViewerActivity.BUNDLE_LAW_TITLE to lawName,
        PdfViewerActivity.BUNDLE_PAGES_TO_LOAD to pagesToLoad.toIntArray(),
        PdfViewerActivity.BUNDLE_IS_DARK_MODE to isInDarkMode
    )
    startActivity(PdfViewerActivity.createIntent(this, bundle))
}