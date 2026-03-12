package com.boryanz.upszakoni.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.database.DocumentsHistoryDao
import com.boryanz.upszakoni.data.local.database.OwnedItemsDao
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.domain.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val overtimeHoursThisYear: String = "-",
    val equipmentCount: Int = 0,
    val documentCount: Int = 0,
    val greetingMessage: String = "",
    val nonWorkingDays: String = "",
    val isAiGeneratorAvailable: Boolean = false,
    val isAppUpdateAvailable: Boolean = false,
)

class HomeViewModel(
    private val bonusSalaryRepository: BonusSalaryRepository,
    private val ownedItemsDao: OwnedItemsDao,
    private val documentsHistoryDao: DocumentsHistoryDao,
    private val remoteConfig: FirebaseRemoteConfig,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
        observeRemoteConfig()
    }

    private fun observeRemoteConfig() {
        val config = remoteConfig.remoteConfigState.value
        _uiState.update {
            it.copy(
                greetingMessage = config.greetingMessage,
                nonWorkingDays = config.nonWorkingDays,
                isAiGeneratorAvailable = config.isAiGeneratorAvailable,
                isAppUpdateAvailable = config.isAppUpdateAvailable,
            )
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            bonusSalaryRepository.getYearlyStatistics().fold(
                onSuccess = { stats ->
                    val totalHours = runCatching {
                        stats.sumOf { it.currentOvertimeHours.toInt() }
                    }.getOrNull()
                    _uiState.update { it.copy(overtimeHoursThisYear = totalHours?.toString() ?: "-") }
                },
                onFailure = {}
            )
        }

        viewModelScope.launch {
            ownedItemsDao.getAllOwnedItems().collect { items ->
                _uiState.update { it.copy(equipmentCount = items.size) }
            }
        }

        viewModelScope.launch {
            documentsHistoryDao.getAll().collect { docs ->
                _uiState.update { it.copy(documentCount = docs.size) }
            }
        }
    }
}
