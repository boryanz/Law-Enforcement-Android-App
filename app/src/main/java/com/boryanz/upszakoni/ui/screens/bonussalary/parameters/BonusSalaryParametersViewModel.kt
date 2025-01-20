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

class BonusSalaryParametersViewModel(
    private val bonusSalaryRepositoryImpl: BonusSalaryRepository,
    private val navigator: NavigationWrapper,
): ViewModel() {

    private val _uiState: MutableStateFlow<BonusSalaryParametersUiState> =
        MutableStateFlow(BonusSalaryParametersUiState())
    val uiState = _uiState.asStateFlow()

    fun onUiEvent(event: BonusSalaryParametersUiEvent) {
        when (event) {
            is BonusSalaryParametersUiEvent.AbsenceLimitChanged -> {
                _uiState.update { it.copy(absenceDaysLimitValue = event.value) }
            }

            is BonusSalaryParametersUiEvent.OvertimeLimitChanged -> {
                _uiState.update { it.copy(overtimeLimitValue = event.value) }
            }

            BonusSalaryParametersUiEvent.SaveParametersClicked -> {
                viewModelScope.launch {
                    bonusSalaryRepositoryImpl.insertTreshold(
                        BonusSalaryTreshold(
                            minimumOvertimeHours = uiState.value.overtimeLimitValue,
                            maximumAbsenceDays = uiState.value.absenceDaysLimitValue
                        )
                    ).fold(
                        onSuccess = { navigator.navigateNext(BonusSalaryDashboardDestination) },
                        onFailure = { /**/ }
                    )
                }
            }
        }
    }
}