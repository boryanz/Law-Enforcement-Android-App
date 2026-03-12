package com.boryanz.upszakoni.ui.navigation.navgraph

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.analytics.AnalyticsLogger
import com.boryanz.upszakoni.customtab.CustomTabLauncher
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.navigation.destinations.InformationScreenDestination
import com.boryanz.upszakoni.ui.navigation.destinations.MoreDestination
import com.boryanz.upszakoni.ui.navigation.destinations.MoreNavGraph
import com.boryanz.upszakoni.ui.navigation.destinations.PartnersDestination
import com.boryanz.upszakoni.ui.navigation.destinations.PhoneNumbersDestination
import com.boryanz.upszakoni.ui.navigation.destinations.PrivacyPolicyDestination
import com.boryanz.upszakoni.ui.screens.informations.InformationScreen
import com.boryanz.upszakoni.ui.screens.more.MoreScreen
import com.boryanz.upszakoni.ui.screens.partners.PartnersScreen
import com.boryanz.upszakoni.ui.screens.phonenumbers.PhoneNumbersScreen
import com.boryanz.upszakoni.ui.screens.privacypolicy.PrivacyPolicyScreen
import com.boryanz.upszakoni.utils.openDialer
import org.koin.compose.koinInject

fun NavGraphBuilder.moreNavGraph(
    navController: NavController,
    context: Context,
    onShareAppClicked: () -> Unit,
    onAppUpdateClicked: () -> Unit,
) {
    navigation<MoreNavGraph>(startDestination = MoreDestination) {
        composable<MoreDestination> {
            MoreScreen(
                onPhoneNumbersClicked = { navController.navigate(PhoneNumbersDestination) },
                onPartnersClicked = { navController.navigate(PartnersDestination) },
                onInfoClicked = { navController.navigate(InformationScreenDestination) },
                onPrivacyPolicyClicked = { navController.navigate(PrivacyPolicyDestination) },
                onShareAppClicked = onShareAppClicked,
                onAppUpdateClicked = onAppUpdateClicked,
                onFeedbackFormClicked = {
                    CustomTabLauncher().launch(
                        context = context,
                        url = context.getString(R.string.feedback_form_url)
                    )
                },
            )
        }

        composable<PhoneNumbersDestination> {
            val analyticsLogger: AnalyticsLogger = koinInject()
            PhoneNumbersScreen(
                onContactClicked = { phoneNumber -> context.openDialer(phoneNumber) },
                onBackClicked = { navController.navigateUp() },
                analyticsLogger = analyticsLogger
            )
        }

        composable<PartnersDestination> {
            PartnersScreen(
                onBackClicked = navController::navigateUp,
                onPartnerClicked = { CustomTabLauncher().launch(context, it) },
            )
        }

        composable<InformationScreenDestination> {
            InformationScreen(onBackClicked = { navController.navigateUp() })
        }

        composable<PrivacyPolicyDestination> {
            PrivacyPolicyScreen(
                backButton = {
                    Icons.Back(onClick = { navController.navigateUp() })
                }
            )
        }
    }
}
