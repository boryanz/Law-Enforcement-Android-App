package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats
import com.boryanz.upszakoni.ui.components.input.TextFieldInput
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiState
import com.boryanz.upszakoni.ui.theme.BaseContent
import com.boryanz.upszakoni.ui.theme.Base_green
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme

@Composable
fun LazyGridLayout(
    uiState: BonusSalaryDashboardUiState,
    paddingValues: PaddingValues,
    onClick: (String) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.heightIn(max = 800.dp),
        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = paddingValues,
        userScrollEnabled = false
    ) {
        items(uiState.monthlyOvertime) { month ->
            val minimumHoursReachedColor = if (month.hasMinimumRequiredHours) Base_green else BaseContent
            TextFieldInput.BaseOutline(
                modifier = Modifier
                    .padding(4.dp)
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
                                    onClick(month.month)
                                }
                            }
                        }
                    },
                textFieldColors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = minimumHoursReachedColor),
                labelText = month.month,
                value = month.overtimeHours,
                onValueChanged = { /* Do nothing as it's read only */ },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LazyGridLayoutPreview() {
    KataSampleAppTheme {
        LazyGridLayout(BonusSalaryDashboardUiState(), PaddingValues(), {})
    }
}

val defaultMonthlyStats = listOf(
    getMonthlyStat("Јануари", 1),
    getMonthlyStat("Февруари", 2),
    getMonthlyStat("Март", 3),
    getMonthlyStat("Април", 4),
    getMonthlyStat("Мај", 5),
    getMonthlyStat("Јуни", 6),
    getMonthlyStat("Јули", 7),
    getMonthlyStat("Август", 8),
    getMonthlyStat("Септември", 9),
    getMonthlyStat("Октомври", 10),
    getMonthlyStat("Ноември", 11),
    getMonthlyStat("Декември", 12),
)

private fun getMonthlyStat(month: String, monthOrder: Int) = MonthlyStats(
    month = month,
    monthOrder = monthOrder,
    currentOvertimeHours = "0",
    currentAbsenceDays = "0",
    currentPaidAbsenceDays = "0"
)