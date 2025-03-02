package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.monthly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.domain.errorhandling.fold
import kotlinx.coroutines.async
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
     * Fetch both totals and daily stats asynchronously.
     */
    fun getDailyStats(month: String) = viewModelScope.launch {

        val dailyStatsDeferred = async {
            bonusSalaryRepository.getDailyStatisticsByMonth(month)
        }

        val totalDataDeferred = async {
            bonusSalaryRepository.getMonthlyStats(month)
        }

        dailyStatsDeferred.await().fold(
            onSuccess = { dayStatsistics ->
                _uiState.update { it.copy(
                    daysInMonth = dayStatsistics,
                ) }
            },
            onFailure = { /*Do nothing for now*/ }
        )

        totalDataDeferred.await().fold(
            onSuccess = { totalData ->
                _uiState.update {
                    it.copy(
                        totalMonthlyOvertime = totalData?.currentOvertimeHours.orEmpty(),
                        totalPaidLeaveDaysUsed = totalData?.currentPaidAbsenceDays.orEmpty(),
                        totalSickDaysUsed = totalData?.currentAbsenceDays.orEmpty()
                    )
                }
            },
            onFailure = { /*Do nothing for now*/ }
        )
        _uiState.update { it.copy(isLoading = false) }
    }
}