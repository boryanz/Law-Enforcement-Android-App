package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.data.NavigationDrawerDestination
import com.boryanz.upszakoni.ui.components.Button
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.Loader
import com.boryanz.upszakoni.ui.components.MonthsGridLayout
import com.boryanz.upszakoni.ui.components.NavigationDrawer
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiEvent.DeleteAllActionButtonClicked
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiEvent.DeleteButtonClicked
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiEvent.UndoDeleteAllActionClicked
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiState.MonthlyOvertime
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiState.OvertimeDonutState
import com.boryanz.upszakoni.ui.theme.UpsTheme

@Composable
fun BonusSalaryDashboardContent(
  uiState: BonusSalaryDashboardUiState,
  onUiEvent: (BonusSalaryDashboardUiEvent) -> Unit,
  onMonthClicked: (String) -> Unit,
  onDrawerItemClicked: (NavigationDrawerDestination) -> Unit,
  onNonWorkingDaysClicked: (String) -> Unit,
) {
  if (uiState.isLoading) Loader() else {
    NavigationDrawer(
      screenTitle = stringResource(R.string.bonus_salary_dashboard_title),
      onItemClicked = onDrawerItemClicked,
      trailingContent = {
        if (uiState.deleteAllState != null) {
          Icons.Undo(onClick = { onUiEvent(UndoDeleteAllActionClicked) })
        } else {
          Icons.Delete(
            modifier = Modifier.testTag("deleteAllAction"),
            onClick = { onUiEvent(DeleteAllActionButtonClicked) }
          )
        }
      }
    ) { paddingValues ->
      Column(
        modifier = Modifier
          .padding(paddingValues)
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
            modifier = Modifier.testTag("deleteAllButton"),
            title = stringResource(
              R.string.bonus_salary_reset_button_format,
              uiState.deleteAllState.buttonClickCounter
            ),
            onClick = { onUiEvent(DeleteButtonClicked) }
          )
          Spacer.Vertical(16.dp)
        }
        Text(stringResource(R.string.bonus_salary_yearly_stats), textAlign = TextAlign.Start)
        Spacer.Vertical(16.dp)
        uiState.overtimeDonutState?.let {
          OvertimeDonutChart(it)
        }
        Spacer.Vertical(8.dp)
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
}

@Composable
private fun OvertimeDonutChart(state: OvertimeDonutState) {
  val animatedProgress by animateFloatAsState(
    targetValue = state.progress,
    animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
    label = "donutProgress"
  )
  val primaryColor = MaterialTheme.colorScheme.primary
  val trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)

  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Box(contentAlignment = Alignment.Center) {
      Canvas(modifier = Modifier.size(160.dp)) {
        val strokeWidth = 22.dp.toPx()
        val inset = strokeWidth / 2f
        val arcSize = Size(size.width - strokeWidth, size.height - strokeWidth)
        val topLeft = Offset(inset, inset)
        drawArc(
          color = trackColor,
          startAngle = -90f,
          sweepAngle = 360f,
          useCenter = false,
          topLeft = topLeft,
          size = arcSize,
          style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
        drawArc(
          color = primaryColor,
          startAngle = -90f,
          sweepAngle = animatedProgress * 360f,
          useCenter = false,
          topLeft = topLeft,
          size = arcSize,
          style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
      }
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
          text = "${state.accumulatedHours}",
          style = MaterialTheme.typography.headlineMedium,
        )
        Text(
          text = "/ ${state.targetHours} ч",
          style = MaterialTheme.typography.bodySmall,
        )
      }
    }
    Spacer.Vertical(4.dp)
    val subtitle = if (state.isGoalReached) {
      "Остварено право на бонус плата!"
    } else {
      "${state.targetHours - state.accumulatedHours} часови до бонус плата"
    }
    Text(
      text = subtitle,
      style = MaterialTheme.typography.bodyMedium,
      textAlign = TextAlign.Center,
    )
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
  overtimeDonutState = OvertimeDonutState(
    accumulatedHours = 90,
    targetHours = 120,
    progress = 0.75f,
    isGoalReached = false,
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
        overtimeDonutState = OvertimeDonutState(
          accumulatedHours = 120,
          targetHours = 120,
          progress = 1f,
          isGoalReached = true,
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
      onDrawerItemClicked = {},

      onNonWorkingDaysClicked = {}
    )
  }
}
