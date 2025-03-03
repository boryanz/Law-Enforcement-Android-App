package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.monthly

import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.components.input.TextFieldInput
import com.boryanz.upszakoni.ui.theme.BaseContent
import com.boryanz.upszakoni.ui.theme.BaseContent1
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
        .fillMaxSize()
        .padding(paddingValues)
        .padding(12.dp)
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
        Text(
          modifier = Modifier.fillMaxWidth(),
          textAlign = TextAlign.Center,
          text = "Вкупна статистика $month"
        )
        Spacer.Vertical(8.dp)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextFieldInput.BaseOutline(
            modifier = Modifier
              .width(90.dp)
              .wrapContentHeight(),
            labelText = "ПЧ",
            textStyle = TextStyle(
              fontSize =  MaterialTheme.typography.titleLarge.fontSize,
              fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
              textAlign = TextAlign.Center,
              fontWeight = FontWeight.ExtraBold
            ),
            isReadOnly = true,
            value = uiState.totalMonthlyOvertime,
            onValueChanged = {}
          )
          Spacer.Horizontal(6.dp)
          TextFieldInput.BaseOutline(
            modifier = Modifier
              .width(90.dp)
              .wrapContentHeight(),
            labelText = "БО",
            textStyle = TextStyle(
              fontSize =  MaterialTheme.typography.titleLarge.fontSize,
              fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
              textAlign = TextAlign.Center,
              fontWeight = FontWeight.ExtraBold
            ),
            isReadOnly = true,
            value = uiState.totalSickDaysUsed,
            onValueChanged = {}
          )
          Spacer.Horizontal(6.dp)
          TextFieldInput.BaseOutline(
            modifier = Modifier
              .width(90.dp)
              .wrapContentHeight(),
            labelText = "ПО",
            textStyle = TextStyle(
              fontSize =  MaterialTheme.typography.titleLarge.fontSize,
              fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
              textAlign = TextAlign.Center,
              fontWeight = FontWeight.ExtraBold
            ),
            isReadOnly = true,
            value = uiState.totalPaidLeaveDaysUsed,
            onValueChanged = {}
          )
        }
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
    items(uiState.daysInMonth, key = { it.id }) { day ->
      val hasHours = (runCatching { day.overtimeHours.toInt() }.getOrNull()
        ?: 0) > 0
      val minimumHoursReachedColor =
        if (hasHours) {
          BaseContent1
        } else {
          BaseContent
        }
      TextFieldInput.BaseOutline(
        modifier = Modifier
          .padding(2.dp)
          .focusable(enabled = false),
        textStyle = TextStyle(
          fontSize = if (hasHours || day.isSickDay || day.isPaidAbsentDay) MaterialTheme.typography.titleLarge.fontSize else MaterialTheme.typography.bodyLarge.fontSize,
          fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
          textAlign = TextAlign.Center,
          fontWeight = if (hasHours || day.isSickDay || day.isPaidAbsentDay) FontWeight.ExtraBold else FontWeight.Thin
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
        isError = day.isSickDay || day.isPaidAbsentDay,
        onValueChanged = { /* Do nothing as it's read only */ },
      )
    }
  }
}


private fun getDayValue(day: DayInMonth): String {
  return when {
    day.isSickDay -> "БО"
    day.isPaidAbsentDay -> "ПО"
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
            dayNumber = 1,
            additionalNote = "",
          ),
          DayInMonth(
            id = 2,
            isSickDay = false,
            isPaidAbsentDay = false,
            overtimeHours = "3",
            month = "Јануари",
            dayNumber = 2,
            additionalNote = "",
          ),
          DayInMonth(
            id = 3,
            isSickDay = false,
            isPaidAbsentDay = false,
            overtimeHours = "12",
            month = "Јануари",
            dayNumber = 3,
            additionalNote = "",
          ),
          DayInMonth(
            id = 4,
            isSickDay = true,
            isPaidAbsentDay = false,
            overtimeHours = "0",
            month = "Јануари",
            dayNumber = 4,
            additionalNote = "",
          ),
          DayInMonth(
            id = 5,
            isSickDay = false,
            isPaidAbsentDay = false,
            overtimeHours = "0",
            month = "Јануари",
            dayNumber = 5,
            additionalNote = "",
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