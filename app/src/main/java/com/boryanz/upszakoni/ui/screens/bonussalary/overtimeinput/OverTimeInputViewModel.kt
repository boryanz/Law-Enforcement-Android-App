package com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.analytics.AnalyticsLogger
import com.boryanz.upszakoni.data.local.database.model.bonussalary.MonthlyStats
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.AbsenceDaysValueChanged
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.FetchMonthlyStats
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.OvertimeHoursValueChanged
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.SaveClicked
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.SickDaysValueChanged
import com.boryanz.upszakoni.utils.mapToOrder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OverTimeInputUiState(
  val overtimeHours: String = "",
  val hasOvertimeHoursError: Boolean = false,
  val paidAbsenceDays: String = "",
  val hasPaidAbsenceDaysError: Boolean = false,
  val sickDays: String = "",
  val hasSickDaysError: Boolean = false,
)

sealed interface OvertimeInputEvent {
  data object StatsSaved : OvertimeInputEvent
}

class OverTimeInputViewModel(
  private val bonusSalaryRepositoryImpl: BonusSalaryRepository,
  private val analyticsLogger: AnalyticsLogger,
) : ViewModel() {

  private val _uiState: MutableStateFlow<OverTimeInputUiState> =
    MutableStateFlow(OverTimeInputUiState())
  val uiState = _uiState.asStateFlow()

  private val _event: MutableSharedFlow<OvertimeInputEvent> = MutableSharedFlow()
  val event = _event.asSharedFlow()

  init {
    analyticsLogger.logScreenEntry("Overtime Input Screen")
  }

  fun onUiEvent(event: OverTimeInputUiEvent) = viewModelScope.launch {
    val absenceDaysValidRange = 0..<31
    val overTimeHoursValidRange = 0..<100

    when (event) {
      is AbsenceDaysValueChanged -> {
        _uiState.update {
          it.copy(
            paidAbsenceDays = event.value,
            hasPaidAbsenceDaysError = runCatching {
              event.value.toInt() !in absenceDaysValidRange
            }.getOrNull() ?: true
          )
        }
      }

      is OvertimeHoursValueChanged -> {
        _uiState.update {
          it.copy(
            overtimeHours = event.value,
            hasOvertimeHoursError = runCatching {
              event.value.toInt() !in overTimeHoursValidRange
            }.getOrNull() ?: true
          )
        }
      }

      is SickDaysValueChanged -> {
        val hasError =
          runCatching { event.value.toInt() !in absenceDaysValidRange }.getOrNull() ?: true
        _uiState.update { it.copy(sickDays = event.value, hasSickDaysError = hasError) }
      }

      is SaveClicked -> {
        analyticsLogger.logButtonClick(
          buttonName = "Save overtime hours button",
          screenName = "Overtime Input Screen"
        )


        bonusSalaryRepositoryImpl.insertMonthlyStats(
          monthlyStats = MonthlyStats(
            month = event.month,
            monthOrder = event.month.mapToOrder(),
            currentOvertimeHours = uiState.value.overtimeHours,
            currentAbsenceDays = uiState.value.sickDays,
            currentPaidAbsenceDays = uiState.value.paidAbsenceDays,
          )
        )
        _event.emit(OvertimeInputEvent.StatsSaved)
      }

      is FetchMonthlyStats -> {
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
            }
          },
          onFailure = {
            _uiState.emit(
              OverTimeInputUiState(
                overtimeHours = "0",
                paidAbsenceDays = "0",
                sickDays = "0"
              )
            )
          }
        )
      }
    }
  }
}