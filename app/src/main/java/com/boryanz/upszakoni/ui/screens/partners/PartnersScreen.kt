package com.boryanz.upszakoni.ui.screens.partners

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold


@Composable
fun PartnersScreen(
    onBackClicked: () -> Unit,
    onPartnerClicked: (url: String) -> Unit,
) {
    UpsScaffold(
        topBarTitle = {
            Text("Наши партнери")
        },
        navigationIcon = {
            Icons.Back(onClick = onBackClicked)
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PartnerItem(
                partnerName = "Синдикат на полиција во Македонија",
                iconRes = R.drawable.sindikat_na_policijata_na_makedonija,
                onClicked = { onPartnerClicked("https://spm.mk/") }
            )
            Spacer.Vertical(6.dp)
            PartnerItem(
                partnerName = "МВР-ППП (Полициски припадници и поранешни)",
                iconRes = R.drawable.fb_logo,
                onClicked = { onPartnerClicked("https://www.facebook.com/groups/2151680138308853/") }
            )
        }
    }

}