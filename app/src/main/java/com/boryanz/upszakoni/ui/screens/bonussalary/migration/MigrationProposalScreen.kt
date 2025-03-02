package com.boryanz.upszakoni.ui.screens.bonussalary.migration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.Button
import com.boryanz.upszakoni.ui.components.Loader
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.BonusSalaryGraphUiAction.MigrationAccepted
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.BonusSalaryUiState.DashboardDestination
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.BonusSalaryUiState.DefaultDestination
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.BonusSalaryUiState.Loading
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.BonusSalaryUiState.ScreenContent
import com.boryanz.upszakoni.ui.theme.UpsTheme
import org.koin.androidx.compose.koinViewModel


sealed interface BonusSalaryGraphUiAction {
    data class MigrationAccepted(val navigateNext: () -> Unit) : BonusSalaryGraphUiAction
}

@Composable
fun MigrationProposalScreen(
    onMigrationAccepted: () -> Unit,
    onMigrationCancelled: (startDestination: Any) -> Unit,
) {
    val viewModel = koinViewModel<MigrationProposalViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.checkIfUserAlreadyHaveData()
    }

    when (uiState) {
        Loading -> Loader()
        is DefaultDestination -> {
            onMigrationCancelled((uiState as DefaultDestination).startDestination)
        }

        is DashboardDestination -> {
            onMigrationCancelled((uiState as DashboardDestination).startDestination)
        }

        ScreenContent -> {
            MigrationProposalContent(
                onMigrationAccepted = {
                    viewModel.onMigrationAccepted(
                        migrationAccepted = MigrationAccepted(
                            navigateNext = onMigrationAccepted
                        )
                    )
                },
                onMigrationCancelled = {
                    viewModel.onMigrationRejected()
                }
            )
        }
    }
}


@Composable
fun MigrationProposalContent(
    onMigrationAccepted: () -> Unit,
    onMigrationCancelled: () -> Unit
) {
    UpsScaffold(
        topBarTitle = { Text(text = "Нова функционалност") },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(all = 12.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp),
                    painter = painterResource(R.drawable.overtime_proposal_200),
                    contentDescription = ""
                )
                Spacer.Vertical(20.dp)
                Text(
                    textAlign = TextAlign.Start,
                    text = "Со новата опција, прекувремените часови ќе се запишуваат по денови во месецот - календар.\n\nДоколку прифатите, старите прекувремени часови ке бидат избришани и ќе мора повтроно да ги внесете часовите со новата опција!\n\nПрепорачуваме да продолжите со новиот начин за подобра прегледност на прекувремените часови."
                )
            }
            Spacer.Vertical(24.dp)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button.Primary("Прифаќам", onClick = onMigrationAccepted)
                Spacer.Vertical(4.dp)
                Button.Outlined("Не прифаќам", onClick = onMigrationCancelled)
            }
        }
    }
}



@PreviewLightDark
@Composable
private fun MigrationProposalPreview() {
    UpsTheme {
        MigrationProposalScreen({}) { }
    }
}