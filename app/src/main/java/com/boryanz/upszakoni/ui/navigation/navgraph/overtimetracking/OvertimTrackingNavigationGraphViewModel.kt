package com.boryanz.upszakoni.ui.navigation.navgraph.overtimetracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OvertimeTrackNavigationGraphViewModel(
  private val bonusSalaryRepository: BonusSalaryRepository
) : ViewModel() {

  private val _hasTresholdSet: MutableStateFlow<Boolean?> = MutableStateFlow(null)
  val hasTresholdSet = _hasTresholdSet.asStateFlow()

  init {
    checkIfUserAlreadyHaveData()
  }

  private fun checkIfUserAlreadyHaveData() = viewModelScope.launch {
    bonusSalaryRepository.getTreshold("bonus_salary_treshold").fold(
      onSuccess = { _hasTresholdSet.emit(it != null) },
      onFailure = { _hasTresholdSet.emit(false) }
    )
  }
}