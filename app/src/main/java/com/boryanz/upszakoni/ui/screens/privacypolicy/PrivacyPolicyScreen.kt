package com.boryanz.upszakoni.ui.screens.privacypolicy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.data.privacyPolictyText
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold

@Composable
fun PrivacyPolicyScreen(
  continueButton: (@Composable () -> Unit)? = null,
  backButton: @Composable () -> Unit,
) {
  UpsScaffold(
    topBarTitle = { Text(text = stringResource(R.string.privacy_policy_title)) },
    navigationIcon = { backButton() }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(it)
        .padding(all = 24.dp)
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.Top
    ) {
      Text(text = privacyPolictyText)
      Spacer(modifier = Modifier.weight(1f))
      continueButton?.let {
        Spacer.Vertical(12.dp)
        it()
      }
    }
  }
}