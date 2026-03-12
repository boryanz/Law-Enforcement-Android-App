package com.boryanz.upszakoni.ui.screens.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.domain.remoteconfig.FirebaseRemoteConfig
import com.boryanz.upszakoni.ui.components.TitleItem
import com.boryanz.upszakoni.ui.components.UpsScaffold
import org.koin.compose.koinInject

@Composable
fun MoreScreen(
    onPhoneNumbersClicked: () -> Unit,
    onPartnersClicked: () -> Unit,
    onInfoClicked: () -> Unit,
    onPrivacyPolicyClicked: () -> Unit,
    onShareAppClicked: () -> Unit,
    onAppUpdateClicked: () -> Unit,
    onFeedbackFormClicked: () -> Unit,
) {
    val remoteConfig: FirebaseRemoteConfig = koinInject()
    val remoteConfigState by remoteConfig.remoteConfigState.collectAsStateWithLifecycle()

    UpsScaffold(
        topBarTitle = {
            Text(
                text = stringResource(R.string.more_tab_title),
                fontWeight = FontWeight.Bold
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            TitleItem(
                title = stringResource(R.string.numbers_ps_svr_skopje),
                onClick = onPhoneNumbersClicked
            )
            HorizontalDivider()

            TitleItem(
                title = stringResource(R.string.our_partners_title),
                onClick = onPartnersClicked
            )
            HorizontalDivider()

            if (remoteConfigState.usefulInformations.isNotEmpty()) {
                TitleItem(
                    title = stringResource(R.string.informations_title),
                    onClick = onInfoClicked
                )
                HorizontalDivider()
            }

            TitleItem(
                title = stringResource(R.string.privacy_policy_title),
                onClick = onPrivacyPolicyClicked
            )
            HorizontalDivider()

            TitleItem(
                title = stringResource(R.string.report_bug_title),
                onClick = onFeedbackFormClicked
            )
            HorizontalDivider()

            TitleItem(
                title = stringResource(R.string.share_app_title),
                onClick = onShareAppClicked
            )

            if (remoteConfigState.isAppUpdateAvailable) {
                HorizontalDivider()
                TitleItem(
                    title = stringResource(R.string.update_available_title),
                    onClick = onAppUpdateClicked
                )
            }
        }
    }
}
