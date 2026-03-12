package com.boryanz.upszakoni.ui.screens.work

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.UpsScaffold

@Composable
fun WorkHomeScreen(
    isAiAvailable: Boolean,
    onOvertimeClicked: () -> Unit,
    onEquipmentClicked: () -> Unit,
    onAiDocsClicked: () -> Unit,
) {
    UpsScaffold(
        topBarTitle = {
            Text(
                text = stringResource(R.string.work_tab_title),
                fontWeight = FontWeight.Bold
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WorkFeatureCard(
                title = stringResource(R.string.overtime_hours_title),
                subtitle = stringResource(R.string.overtime_hours_subtitle),
                icon = { Icon(Icons.Filled.Timelapse, contentDescription = null) },
                onClick = onOvertimeClicked
            )

            WorkFeatureCard(
                title = stringResource(R.string.owned_items_title),
                subtitle = stringResource(R.string.owned_items_subtitle),
                icon = { Icon(Icons.Filled.Inventory, contentDescription = null) },
                onClick = onEquipmentClicked
            )

            if (isAiAvailable) {
                WorkFeatureCard(
                    title = stringResource(R.string.ai_generator_title),
                    subtitle = stringResource(R.string.ai_generator_subtitle),
                    icon = { Icon(Icons.Filled.AutoAwesome, contentDescription = null) },
                    onClick = onAiDocsClicked
                )
            }
        }
    }
}

@Composable
private fun WorkFeatureCard(
    title: String,
    subtitle: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            icon()
            Column {
                Text(text = title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(text = subtitle, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
