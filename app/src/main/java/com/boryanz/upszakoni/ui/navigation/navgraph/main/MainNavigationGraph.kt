package com.boryanz.upszakoni.ui.navigation.navgraph.main

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
import com.boryanz.upszakoni.data.NavigationDrawerDestination.authorities
import com.boryanz.upszakoni.data.NavigationDrawerDestination.bonus_salary_feature
import com.boryanz.upszakoni.data.NavigationDrawerDestination.crimes
import com.boryanz.upszakoni.data.NavigationDrawerDestination.daily_news
import com.boryanz.upszakoni.data.NavigationDrawerDestination.information
import com.boryanz.upszakoni.data.NavigationDrawerDestination.laws
import com.boryanz.upszakoni.data.NavigationDrawerDestination.offenses
import com.boryanz.upszakoni.data.NavigationDrawerDestination.partners
import com.boryanz.upszakoni.data.NavigationDrawerDestination.phone_numbers
import com.boryanz.upszakoni.data.NavigationDrawerDestination.privacy_policy
import com.boryanz.upszakoni.data.NavigationDrawerDestination.wanted_criminals
import com.boryanz.upszakoni.data.NavigationDrawerDestination.writing_guide
import com.boryanz.upszakoni.data.crimesItems
import com.boryanz.upszakoni.data.goldenQuestions
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsDao
import com.boryanz.upszakoni.data.offensesItems
import com.boryanz.upszakoni.data.policeAuthorities
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.navigation.destinations.ArchivedLawsDestination
import com.boryanz.upszakoni.ui.navigation.destinations.CrimesDestination
import com.boryanz.upszakoni.ui.navigation.destinations.GoldenCrimeQuestionsDestination
import com.boryanz.upszakoni.ui.navigation.destinations.InformationScreenDestination
import com.boryanz.upszakoni.ui.navigation.destinations.LawsDestination
import com.boryanz.upszakoni.ui.navigation.destinations.OffensesDestination
import com.boryanz.upszakoni.ui.navigation.destinations.PartnersDestination
import com.boryanz.upszakoni.ui.navigation.destinations.PhoneNumbersDestination
import com.boryanz.upszakoni.ui.navigation.destinations.PoliceAuthoritiesDestination
import com.boryanz.upszakoni.ui.navigation.destinations.PrivacyPolicyAcceptanceDestination
import com.boryanz.upszakoni.ui.navigation.destinations.PrivacyPolicyDestination
import com.boryanz.upszakoni.ui.screens.archivedlaws.ArchivedLawsScreen
import com.boryanz.upszakoni.ui.screens.bonussalary.BonusSalaryActivity
import com.boryanz.upszakoni.ui.screens.common.CommonOffensesAndCrimes
import com.boryanz.upszakoni.ui.screens.crimequestions.GoldenCrimeQuestionsScreen
import com.boryanz.upszakoni.ui.screens.informations.InformationScreen
import com.boryanz.upszakoni.ui.screens.laws.LawsScreen
import com.boryanz.upszakoni.ui.screens.partners.PartnersScreen
import com.boryanz.upszakoni.ui.screens.pdfviewer.PdfViewerActivity
import com.boryanz.upszakoni.ui.screens.phonenumbers.PhoneNumbersScreen
import com.boryanz.upszakoni.ui.screens.policeauthorities.PoliceAuthoritiesScreen
import com.boryanz.upszakoni.ui.screens.privacypolicy.PrivacyPolicyAcceptanceScreen
import com.boryanz.upszakoni.ui.screens.privacypolicy.PrivacyPolicyScreen
import com.boryanz.upszakoni.utils.noEnterTransition
import com.boryanz.upszakoni.utils.noExitTransition
import com.boryanz.upszakoni.utils.openDialer
import com.boryanz.upszakoni.utils.openPdfWithExternalReader
import com.boryanz.upszakoni.utils.supportExternalPdfReader

private const val WANTED_PERSONS_URL = "https://mvr.gov.mk/potragi-ischeznati/potragi"
private const val DAILY_NEWS_URL = "https://mvr.gov.mk/dnevni-bilteni"

@Composable
fun NavigationGraph(
  navHostController: NavHostController = rememberNavController(),
  onShareAppClicked: () -> Unit,
  onAppUpdateClicked: () -> Unit,
) {
  val context = LocalContext.current
  val sharedPrefsDao = remember { SharedPrefsDao }
  val isInDarkMode = isSystemInDarkTheme()
  NavHost(
    navController = navHostController,
    startDestination = if (sharedPrefsDao.isPrivacyPolicyAccepted()) LawsDestination else PrivacyPolicyAcceptanceDestination,
    enterTransition = noEnterTransition,
    exitTransition = noExitTransition
  ) {

    composable<PrivacyPolicyAcceptanceDestination> {
      PrivacyPolicyAcceptanceScreen(
        onContinueClicked = { navHostController.navigate(LawsDestination) },
        backButton = {}
      )
    }

    composable<OffensesDestination> {
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

    composable<CrimesDestination> {
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

    composable<PoliceAuthoritiesDestination> {
      PoliceAuthoritiesScreen(
        topBarTitle = "Полициски овластувања",
        items = policeAuthorities,
        onBackClicked = { navHostController.navigateUp() }
      )
    }

    composable<InformationScreenDestination> {
      InformationScreen(onBackClicked = { navHostController.navigateUp() })
    }

    composable<GoldenCrimeQuestionsDestination> {
      GoldenCrimeQuestionsScreen(
        topBarTitle = "Службена белешка",
        items = goldenQuestions,
        onBackClicked = { navHostController.navigateUp() }
      )
    }

    composable<PhoneNumbersDestination> {
      PhoneNumbersScreen(
        onContactClicked = { phoneNumber ->
          context.openDialer(phoneNumber)
        },
        onBackClicked = { navHostController.navigateUp() }
      )
    }

    composable<PrivacyPolicyDestination> {
      PrivacyPolicyScreen(
        backButton = {
          Icons.Back(onClick = { navHostController.navigateUp() })
        }
      )
    }

    composable<LawsDestination> {
      LawsScreen(
        onItemClick = { navigationDrawerDestination ->
          navHostController.navigateToDrawerDestination(navigationDrawerDestination)
        },
        onLawClick = { lawName ->
          openPdfLaw(lawName, isInDarkMode, context)
        },
        onArchivedLawsClicked = {
          navHostController.navigate(ArchivedLawsDestination)
        },
        onShareAppClicked = onShareAppClicked,
        onAppUpdateClicked = onAppUpdateClicked,
        onFeedbackFormClicked = {
          CustomTabLauncher().launch(
            context,
            url = "https://docs.google.com/forms/d/e/1FAIpQLSekTon0jVIWtwVZEVkBo-mcHZmb55dQumDPs-puUB1ctnackA/viewform?usp=header"
          )
        }
      )
    }

    composable<ArchivedLawsDestination> {
      ArchivedLawsScreen(
        onItemClick = { lawName -> openPdfLaw(lawName, isInDarkMode, context) },
        onBackClicked = { navHostController.navigateUp() }
      )
    }

    // https://spm.mk/
    composable<PartnersDestination> {
      PartnersScreen(
        onBackClicked = navHostController::navigateUp,
        onPartnerClicked = { CustomTabLauncher().launch(context, it) },
      )
    }
  }
}

private fun openPdfLaw(
  lawName: String,
  isInDarkMode: Boolean,
  context: Context
) {
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

fun NavHostController.navigateToDrawerDestination(navigationDrawerDestination: NavigationDrawerDestination) {
  when (navigationDrawerDestination) {
    laws -> navigate(LawsDestination)
    offenses -> navigate(OffensesDestination)
    crimes -> navigate(CrimesDestination)
    authorities -> navigate(PoliceAuthoritiesDestination)
    writing_guide -> navigate(GoldenCrimeQuestionsDestination)
    wanted_criminals -> {
      val customTabLauncher = CustomTabLauncher(
        showTitle = true,
        setUrlBarHiddenEnabled = true
      )
      customTabLauncher.launch(context, WANTED_PERSONS_URL)
    }

    daily_news -> {
      val customTabLauncher = CustomTabLauncher(
        showTitle = true,
        setUrlBarHiddenEnabled = true
      )
      customTabLauncher.launch(context, DAILY_NEWS_URL)
    }

    phone_numbers -> navigate(PhoneNumbersDestination)
    privacy_policy -> navigate(PrivacyPolicyDestination)
    bonus_salary_feature -> context.startActivity(
      BonusSalaryActivity.createIntent(context)
    )

    information -> navigate(InformationScreenDestination)
    partners -> navigate(PartnersDestination)
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