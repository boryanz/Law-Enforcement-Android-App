package com.boryanz.upszakoni.ui.screens.privacypolicy

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import com.boryanz.upszakoni.ui.components.Button
import org.koin.compose.koinInject

@Composable
fun PrivacyPolicyAcceptanceScreen(
  onContinueClicked: () -> Unit,
  backButton: @Composable () -> Unit,
) {
  val localStorage = koinInject<SharedPrefsManager>()
  PrivacyPolicyScreen(
    continueButton = {
      Button.Primary(stringResource(R.string.privacy_policy_continue_button), onClick = {
        localStorage.acceptPrivacyPolicy()
        onContinueClicked()
      })
    },
    backButton = { backButton() }

  )
}