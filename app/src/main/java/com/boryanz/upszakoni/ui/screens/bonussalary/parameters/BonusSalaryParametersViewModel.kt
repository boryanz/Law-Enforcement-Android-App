package com.boryanz.upszakoni.ui.screens.bonussalary.parameters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.database.model.BonusSalaryTreshold
import com.boryanz.upszakoni.domain.BonusSalaryRepository
import com.boryanz.upszakoni.domain.errorhandling.fold
import com.boryanz.upszakoni.ui.navigation.destinations.BonusSalaryDashboardDestination
import com.boryanz.upszakoni.ui.navigation.navigationwrapper.NavigationWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BonusSalaryParametersUiState(
    val overtimeLimitValue: String = "150",
    val absenceDaysLimitValue: String = "21",
    val hasOvertimeLimitError: Boolean = false,
    val hasAbsenceDaysLimitError: Boolean = false,
)

const val BONUS_SALARY_TRESHOLD = "bonus_salary_treshold"

class BonusSalaryParametersViewModel(
    private val bonusSalaryRepositoryImpl: BonusSalaryRepository,
    private val navigator: NavigationWrapper,
) : ViewModel() {

    private val _uiState: MutableStateFlow<BonusSalaryParametersUiState> =
        MutableStateFlow(BonusSalaryParametersUiState())
    val uiState = _uiState.asStateFlow()

    fun onUiEvent(event: BonusSalaryParametersUiEvent) {
        when (event) {
            is BonusSalaryParametersUiEvent.AbsenceLimitChanged -> {
                val hasError = runCatching { event.value.toInt() >= 0}.getOrNull() ?: true
                _uiState.update {
                    it.copy(
                        absenceDaysLimitValue = event.value,
                        hasAbsenceDaysLimitError = hasError
                    )
                }
            }

            is BonusSalaryParametersUiEvent.OvertimeLimitChanged -> {
                val hasError = runCatching { event.value.toInt() < 100 }.getOrNull() ?: true
                _uiState.update {
                    it.copy(
                        overtimeLimitValue = event.value,
                        hasOvertimeLimitError = hasError
                    )
                }
            }

            BonusSalaryParametersUiEvent.SaveParametersClicked -> {
                viewModelScope.launch {
                    bonusSalaryRepositoryImpl.insertTreshold(
                        BonusSalaryTreshold(
                            id = BONUS_SALARY_TRESHOLD,
                            minimumOvertimeHours = uiState.value.overtimeLimitValue,
                            maximumAbsenceDays = uiState.value.absenceDaysLimitValue
                        )
                    ).fold(
                        onSuccess = { navigator.navigateNext(BonusSalaryDashboardDestination) },
                        onFailure = { /**/ }
                    )
                }
            }

            BonusSalaryParametersUiEvent.FetchData -> {
                viewModelScope.launch {
                    bonusSalaryRepositoryImpl.getTreshold(BONUS_SALARY_TRESHOLD).fold(
                        onSuccess = { treshold ->
                            treshold?.let { parameters ->
                                _uiState.update {
                                    it.copy(
                                        overtimeLimitValue = parameters.minimumOvertimeHours,
                                        absenceDaysLimitValue = parameters.maximumAbsenceDays
                                    )
                                }
                            }
                        },
                        onFailure = {}
                    )
                }
            }
        }
    }
}