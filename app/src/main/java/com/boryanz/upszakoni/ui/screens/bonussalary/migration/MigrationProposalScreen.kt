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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.Button
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.theme.UpsTheme
import org.koin.androidx.compose.koinViewModel


sealed interface BonusSalaryGraphUiAction {
    data class MigrationAccepted(val navigateNext: () -> Unit) : BonusSalaryGraphUiAction
}

@Composable
fun MigrationProposalScreen(
    onMigrationAccepted: () -> Unit,
    onMigrationCancelled: () -> Unit,
) {
    val viewModel = koinViewModel<MigrationProposalViewModel>()

    MigrationProposalContent(
        onMigrationAccepted = {
            viewModel.onMigrationAccepted(
                migrationAccepted = BonusSalaryGraphUiAction.MigrationAccepted(
                    navigateNext = onMigrationAccepted
                )
            )
        },
        onMigrationCancelled = { onMigrationCancelled() }
    )
}


@Composable
fun MigrationProposalContent(
    onMigrationAccepted: () -> Unit,
    onMigrationCancelled: () -> Unit
) {
    UpsScaffold(
        topBarTitle = { Text(text = "Нова евиденција") },
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
                    text = "Со новата опција, прекувремените часови ќе се запишуваат по денови во месецот за подобра прегледност. Стариот внес на прекувремени нема да биде сочуван."
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