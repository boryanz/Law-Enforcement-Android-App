package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.domain.BonusSalaryRepository
import com.boryanz.upszakoni.domain.errorhandling.fold
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiState.MonthlyOvertime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class BonusSalaryDashboardUiState(
    val monthlyOvertime: List<MonthlyOvertime> = emptyList()
) {
    data class MonthlyOvertime(
        val month: String = "",
        val overtimeHours: String = "",
        val hasMinimumRequiredHours: Boolean = false,
    )
}

sealed interface BonusSalaryDashboardUiEvent {
    data object FetchMonthlyStats : BonusSalaryDashboardUiEvent
}

class BonusSalaryDashboardViewModel(
    private val bonusSalaryRepository: BonusSalaryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<BonusSalaryDashboardUiState> =
        MutableStateFlow(BonusSalaryDashboardUiState())
    val uiState = _uiState.asStateFlow()


    fun onUiEvent(event: BonusSalaryDashboardUiEvent) = viewModelScope.launch {
        when (event) {
            BonusSalaryDashboardUiEvent.FetchMonthlyStats -> {
                bonusSalaryRepository.getYearlyStatistics().fold(
                    onSuccess = { monthlyStats ->
                        val monthlyOvertime = monthlyStats.map {
                            MonthlyOvertime(
                                month = it.month,
                                overtimeHours = it.currentOvertimeHours,
                                hasMinimumRequiredHours = runCatching { it.currentOvertimeHours.toInt() >= 13 }.getOrElse { false }
                            )
                        }
                        _uiState.emit(BonusSalaryDashboardUiState(monthlyOvertime))
                    },
                    onFailure = { }
                )
            }
        }
    }
}