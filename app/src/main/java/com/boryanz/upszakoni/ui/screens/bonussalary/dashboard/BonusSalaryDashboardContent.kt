package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.AutoAdvancePager
import com.boryanz.upszakoni.ui.components.Button
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.MonthsGridLayout
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.theme.UpsTheme

@Composable
fun BonusSalaryDashboardContent(
    uiState: BonusSalaryDashboardUiState,
    onMonthClicked: (String) -> Unit,
    onBackClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onDeleteAllClicked: () -> Unit,
    onNonWorkingDaysClicked: (String) -> Unit,
) {
    var isDeleteAllClicked by remember {
        mutableStateOf(false)
    }

    var resetClickCounter by remember { mutableIntStateOf(3) }

    UpsScaffold(
        topBarTitle = { Text(stringResource(R.string.bonus_salary_dashboard_title)) },
        navigationIcon = {
            Icons.Back(onClick = onBackClicked)
        },
        trailingIcon = {
            Icons.Edit(onClick = onEditClicked)
            if (isDeleteAllClicked) {
                Icons.Undo(onClick = { isDeleteAllClicked = false })
            } else {
                Icons.Delete(onClick = { isDeleteAllClicked = true })
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
        ) {
            if (isDeleteAllClicked) {
                Text(stringResource(R.string.bonus_salary_reset_hours_title), textAlign = TextAlign.Start)
                Spacer.Vertical(4.dp)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.bonus_salary_reset_warning),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer.Vertical(8.dp)
                Button.Primary(
                    title = stringResource(R.string.bonus_salary_reset_button_format, resetClickCounter),
                    onClick = {
                        resetClickCounter--
                        if (resetClickCounter == 0) {
                            onDeleteAllClicked()
                            return@Primary
                        }
                    })
                Spacer.Vertical(16.dp)
            }
            Text(stringResource(R.string.bonus_salary_yearly_stats), textAlign = TextAlign.Start)
            Spacer.Vertical(8.dp)
            uiState.sliderState?.let {
                AutoAdvancePager(uiState)
            }
            Spacer.Vertical(8.dp)
            Text(stringResource(R.string.bonus_salary_monthly_hours), textAlign = TextAlign.Start)
            MonthsGridLayout(
                uiState = uiState,
                onClick = { onMonthClicked(it) },
                paddingValues = PaddingValues(vertical = 8.dp)
            )
            if (!uiState.nonWorkingDays.isNullOrBlank()) {
                Spacer.Vertical(8.dp)
                Button.Outlined(
                    title = stringResource(R.string.bonus_salary_non_working_days),
                    onClick = { onNonWorkingDaysClicked(uiState.nonWorkingDays) })
            }
        }
    }
}

@Preview
@Composable
private fun BonusSalaryDashboardContentPreview() {
    UpsTheme {
        BonusSalaryDashboardContent(BonusSalaryDashboardUiState(), {}, {}, {}, {}, {})
    }
}