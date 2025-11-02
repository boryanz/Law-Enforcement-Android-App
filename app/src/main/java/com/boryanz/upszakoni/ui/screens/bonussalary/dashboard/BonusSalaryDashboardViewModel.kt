package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfigRepository
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiEvent.DeleteAll
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiEvent.FetchMonthlyStats
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiState.MonthlyOvertime
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiState.SliderState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class BonusSalaryDashboardUiState(
  val monthlyOvertime: List<MonthlyOvertime> = emptyList(),
  val sliderState: List<SliderState?>? = null,
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
}

sealed interface BonusSalaryDashboardUiEvent {
  data object FetchMonthlyStats : BonusSalaryDashboardUiEvent
  data object DeleteAll : BonusSalaryDashboardUiEvent
}

class BonusSalaryDashboardViewModel(
  private val bonusSalaryRepository: BonusSalaryRepository,
  remoteConfigRepository: RemoteConfigRepository,
) : ViewModel() {

  private val _uiState: MutableStateFlow<BonusSalaryDashboardUiState> =
    MutableStateFlow(BonusSalaryDashboardUiState())
  val uiState = _uiState.asStateFlow()

  private val nonWorkingDaysFlag = remoteConfigRepository.remoteConfigState.value.nonWorkingDays

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

      DeleteAll -> bonusSalaryRepository.deleteAllAndGenerateDefaultData()
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