package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.analytics.AnalyticsLogger
import com.boryanz.upszakoni.data.local.database.model.bonussalary.MonthlyStats
import com.boryanz.upszakoni.domain.bonussalary.DaysInMonthDataGenerator
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.domain.remoteconfig.FirebaseRemoteConfig
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiEvent.DeleteAllActionButtonClicked
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiEvent.DeleteButtonClicked
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiEvent.FetchMonthlyStats
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiEvent.UndoDeleteAllActionClicked
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiState.DeleteAllState
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiState.MonthlyOvertime
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiState.SliderState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val DELETE_ALL_BUTTON_COUNTER = 3

data class BonusSalaryDashboardUiState(
  val monthlyOvertime: List<MonthlyOvertime> = emptyList(),
  val sliderState: List<SliderState?>? = null,
  val deleteAllState: DeleteAllState? = null,
  val nonWorkingDays: String? = null,
  val isLoading: Boolean = false,
) {
  data class MonthlyOvertime(
    val month: String = "",
    val overtimeHours: String = "",
  )

  data class SliderState(
    val value: String,
    val progress: Float?,
  )

  data class DeleteAllState(val buttonClickCounter: Int = 0)
}


sealed interface BonusSalaryDashboardEvent {
  data object AllDataDeleted : BonusSalaryDashboardEvent
}


class BonusSalaryDashboardViewModel(
  private val bonusSalaryRepository: BonusSalaryRepository,
  private val generateDefaultDaysInMonthsUseCase: DaysInMonthDataGenerator,
  analyticsLogger: AnalyticsLogger,
  remoteConfigRepository: FirebaseRemoteConfig,
) : ViewModel() {

  private val _uiState: MutableStateFlow<BonusSalaryDashboardUiState> =
    MutableStateFlow(BonusSalaryDashboardUiState())
  val uiState = _uiState.asStateFlow()

  private val _event: MutableSharedFlow<BonusSalaryDashboardEvent> = MutableSharedFlow()
  val event = _event.asSharedFlow()

  private val nonWorkingDaysFlag = remoteConfigRepository.remoteConfigState.value.nonWorkingDays


  init {
    analyticsLogger.logScreenEntry("Bonus Salary Dashboard")
  }

  fun onUiEvent(event: BonusSalaryDashboardUiEvent) = viewModelScope.launch {
    _uiState.update { it.copy(isLoading = true) }
    when (event) {
      FetchMonthlyStats -> {
        bonusSalaryRepository.getYearlyStatistics().fold(
          onSuccess = { yearlyStatistics ->
            val usedUp = getUsedUpState(yearlyStatistics)
            val remainingUntil = getRemainingUntilState(yearlyStatistics)
            val monthlyOvertime = yearlyStatistics.map {
              MonthlyOvertime(
                month = it.month,
                overtimeHours = it.currentOvertimeHours,
              )
            }
            _uiState.emit(
              BonusSalaryDashboardUiState(
                monthlyOvertime = monthlyOvertime,
                sliderState = listOf(usedUp, remainingUntil),
                nonWorkingDays = nonWorkingDaysFlag,
                isLoading = false
              )
            )
          },
          onFailure = {
            /** Currently silent fail is the option **/
          }
        )
      }


      DeleteAllActionButtonClicked -> _uiState.update { uiState ->
        uiState.copy(
          deleteAllState = DeleteAllState(buttonClickCounter = DELETE_ALL_BUTTON_COUNTER),
          isLoading = false
        )
      }

      DeleteButtonClicked -> _uiState.update { uiState ->
        val currentDeleteState = uiState.deleteAllState
        val counter = uiState.deleteAllState?.buttonClickCounter?.minus(1) ?: 0
        if (counter == 0) {
          bonusSalaryRepository.deleteAllAndGenerateDefaultData(defaultData = generateDefaultDaysInMonthsUseCase())
          _uiState.update { uiState -> uiState.copy(deleteAllState = null, isLoading = false) }
          _event.emit(BonusSalaryDashboardEvent.AllDataDeleted)
          return@launch
        }

        uiState.copy(
          isLoading = false,
          deleteAllState = currentDeleteState?.copy(buttonClickCounter = counter)
        )
      }

      UndoDeleteAllActionClicked -> _uiState.update { uiState ->
        uiState.copy(deleteAllState = null, isLoading = false)
      }
    }
  }

  private fun getRemainingUntilState(yearlyStats: List<MonthlyStats>): SliderState? {
    val minimumRequiredHours = bonusSalaryRepository.getMinimumRequiredHours()
    val totalOverTimeHours = runCatching {
      yearlyStats.sumOf { it.currentOvertimeHours.toInt() }
    }.getOrNull() ?: return null

    val remainingUntilHours = minimumRequiredHours - totalOverTimeHours
    if (remainingUntilHours <= 0) return SliderState(
      value = "Остварено право на бонус плата!",
      progress = null
    )

    val progressIndicatorValue =
      ((totalOverTimeHours.toFloat() / bonusSalaryRepository.getMinimumRequiredHours()
        .toFloat()))
    return SliderState(
      value = "$remainingUntilHours часови до бонус плата",
      progress = progressIndicatorValue
    )
  }

  private fun getUsedUpState(yearlyStats: List<MonthlyStats>): SliderState? {
    val usedUpDays = runCatching {
      yearlyStats.sumOf { it.currentAbsenceDays.toInt() + it.currentPaidAbsenceDays.toInt() }
    }.getOrNull() ?: return null

    val progressIndicatorValue =
      ((usedUpDays.toFloat() / bonusSalaryRepository.getMaximumPaidAbsenceDays().toFloat()))
    return SliderState(
      value = "Искористени $usedUpDays денови до сега",
      progress = progressIndicatorValue
    )
  }
}