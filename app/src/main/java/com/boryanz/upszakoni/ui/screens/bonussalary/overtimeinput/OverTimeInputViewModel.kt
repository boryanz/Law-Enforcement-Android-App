package com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.AbsenceDaysValueChanged
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.OvertimeHoursValueChanged
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.SaveClicked
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent.SickDaysValueChanged
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
) : ViewModel() {

  private val _uiState: MutableStateFlow<OverTimeInputUiState> =
    MutableStateFlow(OverTimeInputUiState())
  val uiState = _uiState.asStateFlow()

  private val _event: MutableSharedFlow<OvertimeInputEvent> = MutableSharedFlow()
  val event = _event.asSharedFlow()

  fun onUiEvent(event: OverTimeInputUiEvent) = viewModelScope.launch {
    when (event) {
      is AbsenceDaysValueChanged -> {
        val hasError =
          runCatching { event.value.toInt() >= 31 || event.value.toInt() < 0 }.getOrNull() ?: true
        _uiState.update {
          it.copy(
            paidAbsenceDays = event.value,
            hasPaidAbsenceDaysError = hasError
          )
        }
      }

      is OvertimeHoursValueChanged -> {
        val hasError =
          runCatching { event.value.toInt() >= 100 || event.value.toInt() < 0 }.getOrNull() ?: true
        _uiState.update {
          it.copy(
            overtimeHours = event.value,
            hasOvertimeHoursError = hasError
          )
        }
      }

      is SickDaysValueChanged -> {
        val hasError =
          runCatching { event.value.toInt() >= 31 || event.value.toInt() < 0 }.getOrNull() ?: true
        _uiState.update { it.copy(sickDays = event.value, hasSickDaysError = hasError) }
      }

      is SaveClicked -> {
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
            }
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


  private fun String.mapToOrder() = when (this) {
    "Јануари" -> 1
    "Февруари" -> 2
    "Март" -> 3
    "Април" -> 4
    "Мај" -> 5
    "Јуни" -> 6
    "Јули" -> 7
    "Август" -> 8
    "Септември" -> 9
    "Октомври" -> 10
    "Ноември" -> 11
    "Декември" -> 12
    else -> throw IllegalArgumentException()
  }
}