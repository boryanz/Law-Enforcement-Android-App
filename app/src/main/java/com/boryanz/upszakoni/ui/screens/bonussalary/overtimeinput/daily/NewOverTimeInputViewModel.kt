package com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.domain.errorhandling.fold
import com.boryanz.upszakoni.utils.mapToOrder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NewOverTimeInputUiState(
    val totalOvertime: String = "",
    val hasOvertimeError: Boolean = false,
    val monthName: String = "",
    val dayNumber: Int = 0,
    val monthId: Int = 0,
    val isSickDay: Boolean = false,
    val isPaidLeave: Boolean = false,
    val additionalNote: String = "",
)

data class Totals(
    val overtime: String,
    val paidLeaveDays: String,
    val sickDays: String,
)

class NewOverTimeInputViewModel(
    private val bonusSalaryRepository: BonusSalaryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<NewOverTimeInputUiState> = MutableStateFlow(
        NewOverTimeInputUiState()
    )
    val uiState: StateFlow<NewOverTimeInputUiState>
        get() = _uiState

    private var totals: Totals? = null

    fun onUiEvent(uiEvent: NewOvertimeInputAction) {
        when (uiEvent) {
            is NewOvertimeInputAction.OvertimeValueEntered -> {
                _uiState.update {
                    it.copy(
                        totalOvertime = uiEvent.value,
                        hasOvertimeError = (uiEvent.value.isEmptyOrBlank()
                                && (uiState.value.isSickDay || uiState.value.isPaidLeave))
                                || uiEvent.value.hasOver24OvertimeInADay()
                                || uiEvent.value.hasLessThanZeroOvertime()
                                || ((uiState.value.isSickDay || uiState.value.isPaidLeave) && !uiEvent.value.isEmptyOrBlank())
                    )
                }
            }

            is NewOvertimeInputAction.AdditionalNoteEntered -> {
                _uiState.update {
                    it.copy(additionalNote = uiEvent.value)
                }
            }

            is NewOvertimeInputAction.PaidLeaveClicked -> {
                _uiState.update {
                    it.copy(
                        isPaidLeave = uiEvent.value,
                        hasOvertimeError = _uiState.value.isSickDay && uiEvent.value,
                        totalOvertime = "0"
                    )
                }
            }

            is NewOvertimeInputAction.SickLeaveClicked -> {
                _uiState.update {
                    it.copy(
                        isSickDay = uiEvent.value,
                        hasOvertimeError = _uiState.value.isPaidLeave && uiEvent.value,
                        totalOvertime = "0"
                    )
                }
            }

            is NewOvertimeInputAction.OnCreate -> {
                viewModelScope.launch {
                    bonusSalaryRepository.getDailyStatsById(uiEvent.monthId).fold(
                        onSuccess = { day ->
                            _uiState.update {
                                it.copy(
                                    monthName = day.month,
                                    totalOvertime = day.overtimeHours,
                                    isPaidLeave = day.isPaidAbsentDay,
                                    isSickDay = day.isSickDay,
                                    monthId = day.id,
                                    dayNumber = day.dayNumber,
                                    hasOvertimeError = !uiState.value.totalOvertime.isEmptyOrBlank(),
                                    additionalNote = day.additionalNote
                                )
                            }
                        },
                        onFailure = {
                            /*Do nothing for now*/
                        }
                    )

                    bonusSalaryRepository.getMonthlyStats(uiEvent.monthName).fold(
                        onSuccess = { stats ->
                            stats?.let {
                                totals = Totals(
                                    overtime = stats.currentOvertimeHours,
                                    paidLeaveDays = stats.currentPaidAbsenceDays,
                                    sickDays = stats.currentAbsenceDays,
                                )
                            }

                        },
                        onFailure = { /*Do nothing for now TODO*/ }
                    )
                }
            }

            NewOvertimeInputAction.SaveClicked -> {
                viewModelScope.launch {
                    bonusSalaryRepository.insertDayStats(
                        DayInMonth(
                            id = uiState.value.monthId,
                            isSickDay = uiState.value.isSickDay,
                            isPaidAbsentDay = uiState.value.isPaidLeave,
                            overtimeHours = uiState.value.totalOvertime.takeIf { !it.isEmptyOrBlank() }
                                ?: "0",
                            month = uiState.value.monthName,
                            dayNumber = uiState.value.dayNumber,
                            additionalNote = uiState.value.additionalNote
                        )
                    )

                    bonusSalaryRepository.insertMonthlyStats(
                        MonthlyStats(
                            month = uiState.value.monthName,
                            monthOrder = uiState.value.monthName.mapToOrder(),
                            currentOvertimeHours = calculateTotalOvertime(),
                            currentPaidAbsenceDays = calculateTotalPaidLeaveDays(),
                            currentAbsenceDays = calculateTotalSickDays(),
                        )
                    )
                }
            }

        }
    }

    private fun String.hasLessThanZeroOvertime() =
        runCatching { this.toInt() < 0 }.getOrNull() ?: true

    private fun String.hasOver24OvertimeInADay() =
        runCatching { this.toInt() > 24 }.getOrNull() ?: true

    private fun String.isEmptyOrBlank() = this.isBlank() || this.isEmpty()

    private fun calculateTotalOvertime(): String {
        val oldData = runCatching { totals?.overtime?.toInt() }.getOrNull()
        val newData = runCatching { uiState.value.totalOvertime.toInt() }.getOrNull()
        return if (oldData != null && newData != null) {
            (oldData + newData).toString()
        } else ""
    }

    private fun calculateTotalSickDays(): String {
        val oldData = runCatching { totals?.sickDays?.toInt() }.getOrNull()
        val newData = runCatching { if (uiState.value.isSickDay) 1 else 0 }.getOrNull()
        return if (oldData != null && newData != null) {
            (oldData + newData).toString()
        } else ""
    }

    private fun calculateTotalPaidLeaveDays(): String {
        val oldData = runCatching { totals?.paidLeaveDays?.toInt() }.getOrNull()
        val newData = runCatching { if (uiState.value.isPaidLeave) 1 else 0 }.getOrNull()
        return if (oldData != null && newData != null) {
            (oldData + newData).toString()
        } else ""
    }

}