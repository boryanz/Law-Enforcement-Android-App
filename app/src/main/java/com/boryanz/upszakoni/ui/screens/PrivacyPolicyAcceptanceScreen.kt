package com.boryanz.upszakoni.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.boryanz.upszakoni.storage.sharedprefs.SharedPrefsDao
import com.boryanz.upszakoni.ui.components.Button

@Composable
fun PrivacyPolicyAcceptanceScreen(
    onContinueClicked: () -> Unit,
    onBackClicked: () -> Unit,
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
        onBackClicked = onBackClicked
    )
}