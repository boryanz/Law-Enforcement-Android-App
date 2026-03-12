package com.boryanz.upszakoni.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.QuickActionTile
import com.boryanz.upszakoni.ui.components.QuickStatCard
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onLawsClicked: () -> Unit,
    onOffensesClicked: () -> Unit,
    onOvertimeClicked: () -> Unit,
    onCrimesClicked: () -> Unit,
    onGoldenQuestionsClicked: () -> Unit,
    onPhoneNumbersClicked: () -> Unit,
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UpsScaffold(
        topBarTitle = {
            Text(
                text = stringResource(R.string.app_name),
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Greeting banner
            if (uiState.greetingMessage.isNotBlank()) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = uiState.greetingMessage,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // App update banner
            if (uiState.isAppUpdateAvailable) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {}
                ) {
                    Text(
                        text = stringResource(R.string.update_available_title),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Stats row
            Text(
                text = stringResource(R.string.stats_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickStatCard(
                    label = stringResource(R.string.overtime_hours_title),
                    value = uiState.overtimeHoursThisYear,
                    onClick = onOvertimeClicked,
                    modifier = Modifier.weight(1f)
                )
                QuickStatCard(
                    label = stringResource(R.string.owned_items_title),
                    value = uiState.equipmentCount.toString(),
                    onClick = onOvertimeClicked,
                    modifier = Modifier.weight(1f)
                )
                if (uiState.isAiGeneratorAvailable) {
                    QuickStatCard(
                        label = stringResource(R.string.ai_generator_title),
                        value = uiState.documentCount.toString(),
                        onClick = onOvertimeClicked,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Non-working days banner
            if (uiState.nonWorkingDays.isNotBlank()) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = uiState.nonWorkingDays,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Quick actions grid
            Text(
                text = stringResource(R.string.quick_actions_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickActionTile(
                    title = stringResource(R.string.laws_screen_title),
                    icon = { Icon(Icons.Filled.MenuBook, contentDescription = null) },
                    onClick = onLawsClicked,
                    modifier = Modifier.weight(1f)
                )
                QuickActionTile(
                    title = stringResource(R.string.offenses_title),
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.offenses),
                            contentDescription = null
                        )
                    },
                    onClick = onOffensesClicked,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer.Vertical(0.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickActionTile(
                    title = stringResource(R.string.overtime_hours_title),
                    icon = { Icon(Icons.Filled.Timelapse, contentDescription = null) },
                    onClick = onOvertimeClicked,
                    modifier = Modifier.weight(1f)
                )
                QuickActionTile(
                    title = stringResource(R.string.crimes_title),
                    icon = { Icon(Icons.Filled.Gavel, contentDescription = null) },
                    onClick = onCrimesClicked,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer.Vertical(0.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickActionTile(
                    title = stringResource(R.string.golden_questions_title),
                    icon = { Icon(Icons.Filled.QuestionMark, contentDescription = null) },
                    onClick = onGoldenQuestionsClicked,
                    modifier = Modifier.weight(1f)
                )
                QuickActionTile(
                    title = stringResource(R.string.phone_numbers_more_title),
                    icon = { Icon(Icons.Filled.Phone, contentDescription = null) },
                    onClick = onPhoneNumbersClicked,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
