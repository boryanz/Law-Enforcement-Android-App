package com.boryanz.upszakoni.ui.screens.privacypolicy

import androidx.compose.runtime.Composable
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
      Button.Primary("Продолжи", onClick = {
        localStorage.acceptPrivacyPolicy()
        onContinueClicked()
      })
    },
    backButton = { backButton() }

  )
}