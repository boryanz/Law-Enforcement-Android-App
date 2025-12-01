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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.AutoAdvancePager
import com.boryanz.upszakoni.ui.components.Button
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.MonthsGridLayout
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiEvent.DeleteAllActionButtonClicked
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiEvent.DeleteButtonClicked
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiEvent.UndoDeleteAllActionClicked
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiState.MonthlyOvertime
import com.boryanz.upszakoni.ui.theme.UpsTheme

@Composable
fun BonusSalaryDashboardContent(
  uiState: BonusSalaryDashboardUiState,
  onUiEvent: (BonusSalaryDashboardUiEvent) -> Unit,
  onMonthClicked: (String) -> Unit,
  onBackClicked: () -> Unit,
  onEditClicked: () -> Unit,
  onNonWorkingDaysClicked: (String) -> Unit,
) {
  UpsScaffold(
    topBarTitle = { Text(stringResource(R.string.bonus_salary_dashboard_title)) },
    navigationIcon = {
      Icons.Back(onClick = onBackClicked)
    },
    trailingIcon = {
      Icons.Edit(onClick = onEditClicked)
      if (uiState.deleteAllState != null) {
        Icons.Undo(onClick = { onUiEvent(UndoDeleteAllActionClicked) })
      } else {
        Icons.Delete(onClick = { onUiEvent(DeleteAllActionButtonClicked) })
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
      if (uiState.deleteAllState != null) {
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
          title = stringResource(
            R.string.bonus_salary_reset_button_format,
            uiState.deleteAllState.buttonClickCounter
          ),
          onClick = { onUiEvent(DeleteButtonClicked) }
        )
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

val initialUiState = BonusSalaryDashboardUiState(
  monthlyOvertime = listOf(
    MonthlyOvertime(
      month = "Јануари",
      overtimeHours = "10"
    ),
    MonthlyOvertime(
      month = "Февруари",
      overtimeHours = "10"
    ),
    MonthlyOvertime(
      month = "Март",
      overtimeHours = "10"
    ),
    MonthlyOvertime(
      month = "Април",
      overtimeHours = "10"
    ),
    MonthlyOvertime(
      month = "Мај",
      overtimeHours = "10"
    ),
    MonthlyOvertime(
      month = "Јуни",
      overtimeHours = "10"
    ),
    MonthlyOvertime(
      month = "Јули",
      overtimeHours = "10"
    ),
    MonthlyOvertime(
      month = "Август",
      overtimeHours = "10"
    ),
    MonthlyOvertime(
      month = "Септември",
      overtimeHours = "10"
    ),
    MonthlyOvertime(
      month = "Октомври",
      overtimeHours = "10"
    ),
    MonthlyOvertime(
      month = "Ноември",
      overtimeHours = "10"
    ),
    MonthlyOvertime(
      month = "Декември",
      overtimeHours = "10"
    )
  ),
  sliderState = listOf(
    BonusSalaryDashboardUiState.SliderState(
      value = "Искористени 0 денови до сега",
      progress = 0f
    ),
    BonusSalaryDashboardUiState.SliderState(
      value = "31 часови до бонус плата",
      progress = 0.794702f
    )
  ),
  deleteAllState = null,
  nonWorkingDays = "Неработни денови",
  isLoading = false
)

class BonusSalaryDashboardPreviewProvider : PreviewParameterProvider<BonusSalaryDashboardUiState> {
  override val values: Sequence<BonusSalaryDashboardUiState>
    get() = sequenceOf(
      initialUiState,
      initialUiState.copy(deleteAllState = BonusSalaryDashboardUiState.DeleteAllState(3)),
      initialUiState.copy(deleteAllState = BonusSalaryDashboardUiState.DeleteAllState(2)),
      initialUiState.copy(deleteAllState = BonusSalaryDashboardUiState.DeleteAllState(1)),
      initialUiState.copy(
        sliderState = listOf(
          BonusSalaryDashboardUiState.SliderState(
            value = "Искористени 12 денови до сега",
            progress = 0.55f
          )
        )
      ),
      initialUiState.copy(isLoading = true),
    )

}

@Preview
@Composable
private fun BonusSalaryDashboardContentPreview(
  @PreviewParameter(BonusSalaryDashboardPreviewProvider::class) uiState: BonusSalaryDashboardUiState
) {
  UpsTheme {
    BonusSalaryDashboardContent(
      uiState = uiState,
      onUiEvent = {},
      onMonthClicked = {},
      onBackClicked = {},
      onEditClicked = {},
      onNonWorkingDaysClicked = {}
    )
  }
}
