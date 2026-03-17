package com.boryanz.upszakoni.ui.navigation.navgraph.main

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boryanz.upszakoni.data.NavigationDrawerDestination
import com.boryanz.upszakoni.data.NavigationDrawerDestination.generate_document
import com.boryanz.upszakoni.data.NavigationDrawerDestination.information
import com.boryanz.upszakoni.data.NavigationDrawerDestination.laws
import com.boryanz.upszakoni.data.NavigationDrawerDestination.privacy_policy
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.navigation.destinations.ArchivedLawsDestination
import com.boryanz.upszakoni.ui.navigation.destinations.InformationScreenDestination
import com.boryanz.upszakoni.ui.navigation.destinations.LawsDestination
import com.boryanz.upszakoni.ui.navigation.destinations.PrivacyPolicyAcceptanceDestination
import com.boryanz.upszakoni.ui.navigation.destinations.PrivacyPolicyDestination
import com.boryanz.upszakoni.ui.screens.ai.GenerateDocumentActivity
import com.boryanz.upszakoni.ui.screens.archivedlaws.ArchivedLawsScreen
import com.boryanz.upszakoni.ui.screens.informations.InformationScreen
import com.boryanz.upszakoni.ui.screens.laws.LawsScreen
import com.boryanz.upszakoni.ui.screens.privacypolicy.PrivacyPolicyAcceptanceScreen
import com.boryanz.upszakoni.ui.screens.privacypolicy.PrivacyPolicyScreen
import com.boryanz.upszakoni.utils.noEnterTransition
import com.boryanz.upszakoni.utils.noExitTransition
import com.boryanz.upszakoni.utils.openPdfWithExternalReader
import com.boryanz.upszakoni.utils.supportExternalPdfReader
import org.koin.compose.koinInject


@Composable
fun NavigationGraph(
  navHostController: NavHostController = rememberNavController(),
) {
  val context = LocalContext.current
  val storage: SharedPrefsManager = koinInject()

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


    composable<InformationScreenDestination> {
      InformationScreen(onBackClicked = { navHostController.navigateUp() })
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
          openPdfLaw(lawName, context)
        },
        onArchivedLawsClicked = {
          navHostController.navigate(ArchivedLawsDestination)
        },
      )
    }

    composable<ArchivedLawsDestination> {
      ArchivedLawsScreen(
        onItemClick = { lawName -> openPdfLaw(lawName, context) },
        onBackClicked = { navHostController.navigateUp() }
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
    privacy_policy -> navigate(PrivacyPolicyDestination)
    information -> navigate(InformationScreenDestination)
    generate_document -> context.startActivity(GenerateDocumentActivity.createIntent(context))
  }
}