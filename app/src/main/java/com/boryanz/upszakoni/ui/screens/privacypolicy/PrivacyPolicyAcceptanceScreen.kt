package com.boryanz.upszakoni.ui.screens.privacypolicy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsDao
import com.boryanz.upszakoni.ui.components.Button

@Composable
fun PrivacyPolicyAcceptanceScreen(
    onContinueClicked: () -> Unit,
    backButton: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val sharedPrefsDao = remember { SharedPrefsDao(context) }
    PrivacyPolicyScreen(
        continueButton = {
            Button.Primary("Продолжи", onClick = {
                sharedPrefsDao.acceptPrivacyPolicy()
                onContinueClicked()
            })
        },
        backButton = { backButton() }

    )
}