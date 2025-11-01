package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.monthly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository

import com.boryanz.upszakoni.utils.mapToOrder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OvertimeMonthlyCalendarUiState(
  val isLoading: Boolean = false,
  val daysInMonth: List<DayInMonth> = emptyList(),
  val totalMonthlyOvertime: String = "",
  val totalPaidLeaveDaysUsed: String = "",
  val totalSickDaysUsed: String = "",
)

class OvertimeMonthlyCalendarViewModel(
  private val bonusSalaryRepository: BonusSalaryRepository
) : ViewModel() {

  private val _uiState: MutableStateFlow<OvertimeMonthlyCalendarUiState> =
    MutableStateFlow(OvertimeMonthlyCalendarUiState(isLoading = true))

  val uiState: StateFlow<OvertimeMonthlyCalendarUiState>
    get() = _uiState

  /**
   * First insert freshly added data and then update UI state.
   */
  fun getDailyStats(month: String) = viewModelScope.launch {

    bonusSalaryRepository.getAllDailyStatsByMonth(month).fold(
      onSuccess = { days ->
        val totalOvertime = days.sumOf { runCatching { it.overtimeHours.toInt() }.getOrNull() ?: 0 }.toString()
        val totalSickDays = days.filter { it.isSickDay }.size.toString()
        val totalPaiLeaveDays = days.filter { it.isPaidAbsentDay }.size.toString()

        bonusSalaryRepository.insertMonthlyStats(
          MonthlyStats(
            month = month,
            currentOvertimeHours = totalOvertime,
            currentAbsenceDays = totalSickDays,
            currentPaidAbsenceDays = totalPaiLeaveDays,
            monthOrder = month.mapToOrder()
          )
        )

        _uiState.update {
          it.copy(
            isLoading = false,
            daysInMonth = days,
            totalMonthlyOvertime = totalOvertime,
            totalPaidLeaveDaysUsed = totalPaiLeaveDays,
            totalSickDaysUsed = totalSickDays
          )
        }
      },
      onFailure = {/* Nothing for now */ }
    )

  }
}