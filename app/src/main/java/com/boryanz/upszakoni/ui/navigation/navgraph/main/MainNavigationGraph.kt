package com.boryanz.upszakoni.ui.navigation.navgraph.main

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.analytics.AnalyticsLogger
import com.boryanz.upszakoni.customtab.CustomTabLauncher
import com.boryanz.upszakoni.data.NavigationDrawerDestination
import com.boryanz.upszakoni.data.NavigationDrawerDestination.authorities
import com.boryanz.upszakoni.data.NavigationDrawerDestination.bonus_salary_feature
import com.boryanz.upszakoni.data.NavigationDrawerDestination.crimes
import com.boryanz.upszakoni.data.NavigationDrawerDestination.daily_news
import com.boryanz.upszakoni.data.NavigationDrawerDestination.generate_document
import com.boryanz.upszakoni.data.NavigationDrawerDestination.information
import com.boryanz.upszakoni.data.NavigationDrawerDestination.laws
import com.boryanz.upszakoni.data.NavigationDrawerDestination.offenses
import com.boryanz.upszakoni.data.NavigationDrawerDestination.owned_items
import com.boryanz.upszakoni.data.NavigationDrawerDestination.partners
import com.boryanz.upszakoni.data.NavigationDrawerDestination.phone_numbers
import com.boryanz.upszakoni.data.NavigationDrawerDestination.privacy_policy
import com.boryanz.upszakoni.data.NavigationDrawerDestination.wanted_criminals
import com.boryanz.upszakoni.data.NavigationDrawerDestination.writing_guide
import com.boryanz.upszakoni.data.crimesItems
import com.boryanz.upszakoni.data.goldenQuestions
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import com.boryanz.upszakoni.data.offensesItems
import com.boryanz.upszakoni.data.policeAuthorities
import com.boryanz.upszakoni.ui.components.Icons
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
import com.boryanz.upszakoni.ui.owneditem.OwnedItemsActivity
import com.boryanz.upszakoni.ui.screens.ai.GenerateDocumentActivity
import com.boryanz.upszakoni.ui.screens.bonussalary.BonusSalaryActivity
import com.boryanz.upszakoni.ui.screens.common.CommonOffensesAndCrimes
import com.boryanz.upszakoni.ui.screens.crimequestions.GoldenCrimeQuestionsScreen
import com.boryanz.upszakoni.ui.screens.informations.InformationScreen
import com.boryanz.upszakoni.ui.screens.laws.LawsScreen
import com.boryanz.upszakoni.ui.screens.partners.PartnersScreen
import com.boryanz.upszakoni.ui.screens.phonenumbers.PhoneNumbersScreen
import com.boryanz.upszakoni.ui.screens.policeauthorities.PoliceAuthoritiesScreen
import com.boryanz.upszakoni.ui.screens.privacypolicy.PrivacyPolicyAcceptanceScreen
import com.boryanz.upszakoni.ui.screens.privacypolicy.PrivacyPolicyScreen
import com.boryanz.upszakoni.utils.noEnterTransition
import com.boryanz.upszakoni.utils.noExitTransition
import com.boryanz.upszakoni.utils.openDialer
import com.boryanz.upszakoni.utils.openPdfWithExternalReader
import com.boryanz.upszakoni.utils.supportExternalPdfReader
import org.koin.compose.koinInject


@Composable
fun NavigationGraph(
  navHostController: NavHostController = rememberNavController(),
  onShareAppClicked: () -> Unit,
  onAppUpdateClicked: () -> Unit,
) {
  val context = LocalContext.current
  val storage: SharedPrefsManager = koinInject()
  val analyticsLogger: AnalyticsLogger = koinInject()

  NavHost(
    navController = navHostController,
    startDestination = if (storage.isPrivacyPolicyAccepted()) LawsDestination else PrivacyPolicyAcceptanceDestination,
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
        title = stringResource(R.string.offenses_title),
        commonCrimesItems = offensesItems,
        onBackClicked = { navHostController.navigateUp() }
      )
    }

    composable<CrimesDestination> {
      CommonOffensesAndCrimes(
        title = stringResource(R.string.crimes_title),
        commonCrimesItems = crimesItems,
        onBackClicked = { navHostController.navigateUp() }
      )
    }

    composable<PoliceAuthoritiesDestination> {
      PoliceAuthoritiesScreen(
        topBarTitle = stringResource(R.string.police_authorities_title),
        items = policeAuthorities,
        onBackClicked = { navHostController.navigateUp() }
      )
    }

    composable<InformationScreenDestination> {
      InformationScreen(onBackClicked = { navHostController.navigateUp() })
    }

    composable<GoldenCrimeQuestionsDestination> {
      GoldenCrimeQuestionsScreen(
        topBarTitle = stringResource(R.string.golden_questions_title),
        items = goldenQuestions,
        onBackClicked = { navHostController.navigateUp() },
        analyticsLogger = analyticsLogger
      )
    }

    composable<PhoneNumbersDestination> {
      PhoneNumbersScreen(
        onContactClicked = { phoneNumber ->
          context.openDialer(phoneNumber)
        },
        onBackClicked = { navHostController.navigateUp() },
        analyticsLogger = analyticsLogger
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
        onShareAppClicked = onShareAppClicked,
        onAppUpdateClicked = onAppUpdateClicked,
        onFeedbackFormClicked = {
          CustomTabLauncher().launch(
            context = context,
            url = context.getString(R.string.feedback_form_url)
          )
        },
        onError = { it.handle(context) },
        onPdfReady = { openPdfLaw(lawName = it, context = context) }
      )
    }

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
  context: Context
) {
  if (supportExternalPdfReader(context)) {
    openPdfWithExternalReader(context, lawName)
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
      customTabLauncher.launch(context, context.getString(R.string.wanted_persons_url))
    }

    daily_news -> {
      val customTabLauncher = CustomTabLauncher(
        showTitle = true,
        setUrlBarHiddenEnabled = true
      )
      customTabLauncher.launch(context, context.getString(R.string.daily_news_url))
    }

    phone_numbers -> navigate(PhoneNumbersDestination)
    privacy_policy -> navigate(PrivacyPolicyDestination)
    bonus_salary_feature -> context.startActivity(
      BonusSalaryActivity.createIntent(context)
    )

    owned_items -> context.startActivity(OwnedItemsActivity.createIntent(context))
    information -> navigate(InformationScreenDestination)
    partners -> navigate(PartnersDestination)
    generate_document -> context.startActivity(GenerateDocumentActivity.createIntent(context))
  }
}