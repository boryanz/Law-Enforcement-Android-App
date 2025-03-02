package com.boryanz.upszakoni.ui.screens.bonussalary.migration

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsDao
import com.boryanz.upszakoni.domain.GenerateDaysInMonthsUseCase
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.domain.errorhandling.fold
import com.boryanz.upszakoni.ui.navigation.destinations.BonusSalaryDashboardDestination
import com.boryanz.upszakoni.ui.navigation.destinations.ParametersDestination
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.BonusSalaryGraphUiAction.MigrationAccepted
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface BonusSalaryUiState {
    data object Loading : BonusSalaryUiState
    data object ScreenContent : BonusSalaryUiState
    data class DefaultDestination(val startDestination: Any) : BonusSalaryUiState
    data class DashboardDestination(val startDestination: Any) : BonusSalaryUiState
}

class MigrationProposalViewModel(
    private val bonusSalaryRepository: BonusSalaryRepository,
    private val generateDaysInMonthsUseCase: GenerateDaysInMonthsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<BonusSalaryUiState> =
        MutableStateFlow(BonusSalaryUiState.Loading)
    val uiState = _uiState.asStateFlow()

    /**
     * New users are automatically accepting new overtime tracking feature.
     * Save decision to shared preferences,delete the whole DB and add new daily entries!
     */
    @SuppressLint("NewApi")
    fun onMigrationAccepted(migrationAccepted: MigrationAccepted) =
        viewModelScope.launch {
            bonusSalaryRepository.deleteAllAndGenerateDefaultData()
            SharedPrefsDao.acceptOvertimeTrackingMigration()
            migrationAccepted.navigateNext()
        }

    /**
     * New or old user rejected usage of new tracking system.
     * Save decision to shared preferences
     */
    fun onMigrationRejected() {
        viewModelScope.launch(Dispatchers.IO) {
            SharedPrefsDao.rejectOverTimeTrackingMigration().also {
                checkIfUserAlreadyHaveData()
            }
        }
    }

    /**
     * If parameters are already in db, proceed to BonusSalaryDashboard.
     * Verify this only if user rejected the migration.
     */
    fun checkIfUserAlreadyHaveData() = viewModelScope.launch(Dispatchers.IO) {
        if (!SharedPrefsDao.hasUserRejectedOvertimeTrackingMigration()) {
            _uiState.emit(BonusSalaryUiState.ScreenContent)
            return@launch
        }

        bonusSalaryRepository.getTreshold("bonus_salary_treshold").fold(
            onSuccess = {
                if (it != null) {
                    _uiState.emit(
                        BonusSalaryUiState.DashboardDestination(
                            BonusSalaryDashboardDestination
                        )
                    )
                } else {
                    _uiState.emit(
                        BonusSalaryUiState.DefaultDestination(
                            ParametersDestination
                        )
                    )
                }
            },
            onFailure = {
                _uiState.emit(
                    BonusSalaryUiState.DefaultDestination(
                        ParametersDestination
                    )
                )
            }
        )
    }
}