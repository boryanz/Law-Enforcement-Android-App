package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.monthly

import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.ReadOnlyRowItem
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.components.input.TextFieldInput

@Composable
fun OvertimeMonthlyCalendarContent(
    month: String,
    onBackClicked: () -> Unit,
    onDayInMonthClicked: () -> Unit,
) {
    UpsScaffold(
        topBarTitle = { Text(month) },
        navigationIcon = {
            Icons.Back(onClick = onBackClicked)
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 12.dp, vertical = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Календар",
                    textAlign = TextAlign.Start
                )
                Spacer.Vertical(8.dp)
                HorizontalDivider()
                Spacer.Vertical(4.dp)
                DaysInMonthGridLayout(it, onClick = {onDayInMonthClicked()})
                Spacer.Vertical(8.dp)
            }
            Spacer.Vertical(24.dp)
            Column {
                ReadOnlyRowItem("Денови платено отсуство = 1")
                ReadOnlyRowItem("Вкупно прекувремени = 23")
                ReadOnlyRowItem("Вкупно боледување = 3")
            }
        }
    }
}


@Composable
fun DaysInMonthGridLayout(
    paddingValues: PaddingValues,
    onClick: (String) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.heightIn(max = 800.dp),
        columns = GridCells.Adaptive(minSize = 70.dp),
        userScrollEnabled = false
    ) {
        items((1..31).map { it.toString() }) { daysInMonth ->
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
                                    onClick(daysInMonth)
                                }
                            }
                        }
                    },
                labelText = daysInMonth,
                value = if (30 / daysInMonth.toInt() % 2 == 0) "БО" else "0",
                isError = 30 / daysInMonth.toInt() % 2 == 0,
                onValueChanged = { /* Do nothing as it's read only */ },
            )
        }
    }
}
