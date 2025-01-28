package com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats
import com.boryanz.upszakoni.domain.BonusSalaryRepository
import com.boryanz.upszakoni.domain.errorhandling.fold
import com.boryanz.upszakoni.ui.navigation.navigationwrapper.NavigationWrapper
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.AbsenceDaysValueChanged
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.OvertimeHoursValueChanged
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.SaveClicked
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.SickDaysValueChanged
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OverTimeInputUiState(
    val overtimeHours: String = "",
    val paidAbsenceDays: String = "",
    val sickDays: String = "",
)

class OverTimeInputViewModel(
    private val bonusSalaryRepositoryImpl: BonusSalaryRepository,
    private val navigator: NavigationWrapper
) : ViewModel() {

    private val _uiState: MutableStateFlow<OverTimeInputUiState> =
        MutableStateFlow(OverTimeInputUiState())
    val uiState = _uiState.asStateFlow()


    fun onUiEvent(event: OverTimeInputUiEvent) = viewModelScope.launch {
        when (event) {
            is AbsenceDaysValueChanged -> {
                _uiState.update { it.copy(paidAbsenceDays = event.value) }
            }

            is OvertimeHoursValueChanged -> {
                _uiState.update { it.copy(overtimeHours = event.value) }
            }

            is SickDaysValueChanged -> {
                _uiState.update { it.copy(sickDays = event.value) }
            }

            is SaveClicked -> {
                bonusSalaryRepositoryImpl.insertMonthlyStats(
                    monthlyStats = MonthlyStats(
                        month = event.month,
                        currentOvertimeHours = uiState.value.overtimeHours,
                        currentAbsenceDays = uiState.value.sickDays,
                        currentPaidAbsenceDays = uiState.value.paidAbsenceDays
                    )
                )
                navigator.navigateBack()
            }

            is OverTimeInputUiEvent.FetchMonthlyStats -> {
                bonusSalaryRepositoryImpl.getMonthlyStats(event.month).fold(
                    onSuccess = { stats ->
                        stats?.let {
                            _uiState.emit(
                                OverTimeInputUiState(
                                    overtimeHours = it.currentOvertimeHours,
                                    paidAbsenceDays = it.currentPaidAbsenceDays,
                                    sickDays = it.currentAbsenceDays
                                )
                            )
                        } ?: _uiState.emit(getDefaultUiState())

                    },
                    onFailure = {
                        _uiState.emit(getDefaultUiState())
                    }
                )
            }
        }
    }

    private fun getDefaultUiState() = OverTimeInputUiState(
        overtimeHours = "0",
        paidAbsenceDays = "0",
        sickDays = "0"
    )
}