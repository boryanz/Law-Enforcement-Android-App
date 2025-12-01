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
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BonusSalaryDashboardViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private lateinit var viewmodel: BonusSalaryDashboardViewModel

  private val expUsedUpHours = BonusSalaryDashboardUiState.SliderState(
    value = "Искористени 0 денови до сега",
    progress = 0f
  )

  private val expUntilBonusSalary = BonusSalaryDashboardUiState.SliderState(
    value = "31 часови до бонус плата",
    progress = 0.794702f
  )

  private val defaultRemoteConfig = RemoteConfig(
    isAppUpdateAvailable = false,
    shouldBackportOvertimeTracking = false,
    greetingMessage = "",
    usefulInformations = "",
    nonWorkingDays = "no working days"
  )

  private fun createViewmodel(
    bonusSalaryRepository: FakeBonusSalaryRepository = FakeBonusSalaryRepository(Treshold.HaveTreshold),
    remoteConfig: RemoteConfig = defaultRemoteConfig
  ) {
    viewmodel = BonusSalaryDashboardViewModel(
      bonusSalaryRepository = bonusSalaryRepository,
      generateDefaultDaysInMonthsUseCase = FakeGenerateDaysInMonthsUseCase(),
      analyticsLogger = FakeAnalyticsManager(),
      remoteConfigRepository = FakeRemoteConfigRepository(remoteConfig)
    )
  }


  @Test
  fun `fetch monthly overtime hours successfully where all months have data`() = runTest {
    //Given
    createViewmodel()

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

  @Test
  fun `when delete all button is clicked, then enable delete all button`() = runTest {
    //Given
    createViewmodel()
    //When
    viewmodel.onUiEvent(BonusSalaryDashboardUiEvent.FetchMonthlyStats)
    viewmodel.onUiEvent(BonusSalaryDashboardUiEvent.DeleteAllActionButtonClicked)

    //Then
    assertEquals(
      BonusSalaryDashboardUiState(
        monthlyOvertime = monthlyOvertimeState,
        sliderState = listOf(expUsedUpHours, expUntilBonusSalary),
        deleteAllState = BonusSalaryDashboardUiState.DeleteAllState(buttonClickCounter = 3),
        nonWorkingDays = "no working days",
        isLoading = false
      ),
      viewmodel.uiState.value
    )
  }

  @Test
  fun `when undo delete button is clicked, reset delete all data`() = runTest {
    //Given
    createViewmodel()
    //When
    viewmodel.onUiEvent(BonusSalaryDashboardUiEvent.FetchMonthlyStats)
    viewmodel.onUiEvent(BonusSalaryDashboardUiEvent.DeleteAllActionButtonClicked)
    viewmodel.onUiEvent(BonusSalaryDashboardUiEvent.UndoDeleteAllActionClicked)

    //Then
    assertEquals(
      BonusSalaryDashboardUiState(
        monthlyOvertime = monthlyOvertimeState,
        sliderState = listOf(expUsedUpHours, expUntilBonusSalary),
        deleteAllState = null,
        nonWorkingDays = "no working days",
        isLoading = false
      ),
      viewmodel.uiState.value
    )
  }

  @Test
  fun `when delete button is clicked, reduce counter by 1`() = runTest {
    //Given
    createViewmodel()
    //When
    viewmodel.onUiEvent(BonusSalaryDashboardUiEvent.FetchMonthlyStats)
    viewmodel.onUiEvent(BonusSalaryDashboardUiEvent.DeleteAllActionButtonClicked)
    viewmodel.onUiEvent(BonusSalaryDashboardUiEvent.DeleteButtonClicked)

    //Then
    assertEquals(
      BonusSalaryDashboardUiState(
        monthlyOvertime = monthlyOvertimeState,
        sliderState = listOf(expUsedUpHours, expUntilBonusSalary),
        deleteAllState = BonusSalaryDashboardUiState.DeleteAllState(buttonClickCounter = 2),
        nonWorkingDays = "no working days",
        isLoading = false
      ),
      viewmodel.uiState.value
    )
  }

  @Test
  fun `when delete all counter reaches 0 then delete all the data and emit event`() = runTest {
    //Given
    val expectedUiState = BonusSalaryDashboardUiState(
      monthlyOvertime = monthlyOvertimeState,
      sliderState = listOf(expUsedUpHours, expUntilBonusSalary),
      deleteAllState = BonusSalaryDashboardUiState.DeleteAllState(buttonClickCounter = 2),
      nonWorkingDays = "no working days",
      isLoading = false
    )
    val fakeBonusSalaryRepository = FakeBonusSalaryRepository(Treshold.HaveTreshold)
    createViewmodel(bonusSalaryRepository = fakeBonusSalaryRepository)
    //When
    viewmodel.onUiEvent(BonusSalaryDashboardUiEvent.FetchMonthlyStats)
    viewmodel.onUiEvent(BonusSalaryDashboardUiEvent.DeleteAllActionButtonClicked)

    //Then
    assertEquals(
      expectedUiState.copy(deleteAllState = BonusSalaryDashboardUiState.DeleteAllState(3)),
      viewmodel.uiState.value
    )

    //When
    viewmodel.onUiEvent(BonusSalaryDashboardUiEvent.DeleteButtonClicked)

    //Then
    assertEquals(
      expectedUiState.copy(deleteAllState = BonusSalaryDashboardUiState.DeleteAllState(2)),
      viewmodel.uiState.value
    )

    //When
    viewmodel.onUiEvent(BonusSalaryDashboardUiEvent.DeleteButtonClicked)

    //Then
    assertEquals(
      expectedUiState.copy(deleteAllState = BonusSalaryDashboardUiState.DeleteAllState(1)),
      viewmodel.uiState.value
    )

    //When
    viewmodel.onUiEvent(BonusSalaryDashboardUiEvent.DeleteButtonClicked)

    //Then
    assertEquals(
      expectedUiState.copy(deleteAllState = null),
      viewmodel.uiState.value
    )

    //Assert that delete all data was invoked.
    assertTrue(fakeBonusSalaryRepository.isAllDataDeleted)
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