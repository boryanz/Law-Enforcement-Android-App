package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.monthly

import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.ReadOnlyRowItem
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.components.input.TextFieldInput
import com.boryanz.upszakoni.ui.theme.BaseContent
import com.boryanz.upszakoni.ui.theme.Base_green
import com.boryanz.upszakoni.ui.theme.UpsTheme

@Composable
fun OvertimeMonthlyCalendarContent(
    uiState: OvertimeMonthlyCalendarUiState,
    month: String,
    onBackClicked: () -> Unit,
    onDayInMonthClicked: (DayInMonth) -> Unit,
) {
    UpsScaffold(
        topBarTitle = { Text("Календар за $month") },
        navigationIcon = {
            Icons.Back(onClick = onBackClicked)
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 12.dp, vertical = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.Top) {
                Spacer.Vertical(4.dp)
                DaysInMonthGridLayout(
                    uiState = uiState,
                    onClick = { onDayInMonthClicked(it) }
                )
                Spacer.Vertical(8.dp)
            }
            Spacer.Vertical(24.dp)
            Column {
                ReadOnlyRowItem("Вкупно прекувремени = ${uiState.totalMonthlyOvertime}")
                ReadOnlyRowItem("Вкупно боледување = ${uiState.totalSickDaysUsed}")
                ReadOnlyRowItem("Денови платено отсуство = ${uiState.totalPaidLeaveDaysUsed}")
            }
        }
    }
}


@Composable
fun DaysInMonthGridLayout(
    uiState: OvertimeMonthlyCalendarUiState,
    onClick: (DayInMonth) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.heightIn(max = 800.dp),
        columns = GridCells.Adaptive(minSize = 70.dp),
        userScrollEnabled = false
    ) {
        items(uiState.daysInMonth) { day ->
            val minimumHoursReachedColor =
                if ((runCatching { day.overtimeHours.toInt() }.getOrNull() ?: 0) > 0 || day.isPaidAbsentDay) {
                    Base_green
                } else {
                    BaseContent
                }
            TextFieldInput.BaseOutline(
                modifier = Modifier
                    .padding(2.dp)
                    .focusable(enabled = false),
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
                    textAlign = TextAlign.Center
                ),
                isReadOnly = true,
                interactionSource = remember { MutableInteractionSource() }
                    .also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Release) {
                                    onClick(day)
                                }
                            }
                        }
                    },
                labelText = day.dayNumber.toString(),
                value = getDayValue(day),
                textFieldColors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = minimumHoursReachedColor),
                isError = day.isSickDay,
                onValueChanged = { /* Do nothing as it's read only */ },
            )
        }
    }
}


private fun getDayValue(day: DayInMonth): String {
    return when {
        day.isSickDay ->  "\uD83E\uDD12"
        day.isPaidAbsentDay -> "\u2708\uFE0F"
        else -> day.overtimeHours
    }
}

@PreviewLightDark
@Composable
private fun OvertimeMonthlyCalendarPreview() {
    UpsTheme {
        OvertimeMonthlyCalendarContent(
            uiState = OvertimeMonthlyCalendarUiState(
                daysInMonth = listOf(
                    DayInMonth(
                        id = 1,
                        isSickDay = false,
                        isPaidAbsentDay = false,
                        overtimeHours = "23",
                        month = "Јануари",
                        dayNumber = 1
                    ),
                    DayInMonth(
                        id = 2,
                        isSickDay = false,
                        isPaidAbsentDay = false,
                        overtimeHours = "3",
                        month = "Јануари",
                        dayNumber = 2
                    ),
                    DayInMonth(
                        id = 3,
                        isSickDay = false,
                        isPaidAbsentDay = false,
                        overtimeHours = "12",
                        month = "Јануари",
                        dayNumber = 3
                    ),
                    DayInMonth(
                        id = 4,
                        isSickDay = true,
                        isPaidAbsentDay = false,
                        overtimeHours = "0",
                        month = "Јануари",
                        dayNumber = 4
                    ),
                    DayInMonth(
                        id = 5,
                        isSickDay = false,
                        isPaidAbsentDay = false,
                        overtimeHours = "0",
                        month = "Јануари",
                        dayNumber = 5
                    )
                ),
                totalMonthlyOvertime = "55",
                totalSickDaysUsed = "2",
                totalPaidLeaveDaysUsed = "1"
            ),
            month = "Јануари",
            onBackClicked = {},
            onDayInMonthClicked = {}
        )
    }
}