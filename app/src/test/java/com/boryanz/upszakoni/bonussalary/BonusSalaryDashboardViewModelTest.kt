package com.boryanz.upszakoni.bonussalary

import com.boryanz.upszakoni.MainDispatcherRule
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfig
import com.boryanz.upszakoni.fakes.FakeAnalyticsManager
import com.boryanz.upszakoni.fakes.FakeBonusSalaryRepository
import com.boryanz.upszakoni.fakes.FakeGenerateDaysInMonthsUseCase
import com.boryanz.upszakoni.fakes.FakeRemoteConfigRepository
import com.boryanz.upszakoni.fakes.Treshold
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiEvent
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiState
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiState.MonthlyOvertime
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BonusSalaryDashboardViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private lateinit var viewmodel: BonusSalaryDashboardViewModel


  @Test
  fun `fetch monthly overtime hours successfully where all months have data`() = runTest {
    //Given
    val expUsedUpHours = BonusSalaryDashboardUiState.SliderState(
      value = "Искористени 0 денови до сега",
      progress = 0f
    )
    val expUntilBonusSalary = BonusSalaryDashboardUiState.SliderState(
      value = "31 часови до бонус плата",
      progress = 0.794702f
    )
    viewmodel = BonusSalaryDashboardViewModel(
      bonusSalaryRepository = FakeBonusSalaryRepository(Treshold.HaveTreshold),
      generateDefaultDaysInMonthsUseCase = FakeGenerateDaysInMonthsUseCase(),
      analyticsLogger = FakeAnalyticsManager(),
      remoteConfigRepository = FakeRemoteConfigRepository(
        RemoteConfig(
          isAppUpdateAvailable = false,
          shouldBackportOvertimeTracking = false,
          greetingMessage = "",
          usefulInformations = "",
          nonWorkingDays = "no working days"
        )
      )
    )

    //When
    viewmodel.onUiEvent(event = BonusSalaryDashboardUiEvent.FetchMonthlyStats)

    //Then
    assertEquals(
      BonusSalaryDashboardUiState(
        monthlyOvertime = monthlyOvertimeState,
        sliderState = listOf(expUsedUpHours, expUntilBonusSalary),
        nonWorkingDays = "no working days",
        isLoading = false
      ),
      viewmodel.uiState.value
    )
  }
}

private val monthlyOvertimeState = listOf(
  MonthlyOvertime(
    month = "Јануари",
    overtimeHours = "10"
  ),
  MonthlyOvertime(
    month = "Февруари",
    overtimeHours = "10"
  ),
  MonthlyOvertime(
    month = "Март",
    overtimeHours = "10"
  ),
  MonthlyOvertime(
    month = "Април",
    overtimeHours = "10"
  ),
  MonthlyOvertime(
    month = "Мај",
    overtimeHours = "10"
  ),
  MonthlyOvertime(
    month = "Јуни",
    overtimeHours = "10"
  ),
  MonthlyOvertime(
    month = "Јули",
    overtimeHours = "10"
  ),
  MonthlyOvertime(
    month = "Август",
    overtimeHours = "10"
  ),
  MonthlyOvertime(
    month = "Септември",
    overtimeHours = "10"
  ),
  MonthlyOvertime(
    month = "Октомври",
    overtimeHours = "10"
  ),
  MonthlyOvertime(
    month = "Ноември",
    overtimeHours = "10"
  ),
  MonthlyOvertime(
    month = "Декември",
    overtimeHours = "10"
  )
)