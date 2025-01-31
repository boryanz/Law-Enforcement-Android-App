package com.boryanz.upszakoni.ui.screens.bonussalary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.domain.errorhandling.fold
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BonusSalaryViewModel(
    private val bonusSalaryRepository: BonusSalaryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val uiState = _uiState.asStateFlow()

    init {
        getStartDestination()
    }

    /**
     * If parameters are already in db, proceed to BonusSalaryDashboard.
     */
    private fun getStartDestination() = viewModelScope.launch {
        bonusSalaryRepository.getTreshold("bonus_salary_treshold").fold(
            onSuccess = { _uiState.emit(it != null) },
            onFailure = { _uiState.emit(false) }
        )
    }
}