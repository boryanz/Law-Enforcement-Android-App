package com.boryanz.upszakoni.overtime

import com.boryanz.upszakoni.MainDispatcherRule
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.fakes.FakeBonusSalaryRepository
import com.boryanz.upszakoni.fakes.Treshold
import com.boryanz.upszakoni.ui.navigation.navigationwrapper.NavigationWrapper
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiEvent
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputUiState
import com.boryanz.upszakoni.ui.screens.bonussalary.overtimeinput.OverTimeInputViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OverTimeInputViewModelTest {

  @get:Rule
  val mockkRule = MockKRule(this)

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @MockK
  private lateinit var navigationWrapper: NavigationWrapper


  @Test
  fun `when absence days value is changed and it's in valid range then update state`() = runTest {
    //Given
    val viewModel = OverTimeInputViewModel(
      bonusSalaryRepositoryImpl = FakeBonusSalaryRepository(Treshold.HaveTreshold),
      navigator = navigationWrapper
    )
    //When
    val value = "12"
    viewModel.onUiEvent(OverTimeInputUiEvent.AbsenceDaysValueChanged(value))
    advanceUntilIdle()

    //Then
    assertEquals(
      OverTimeInputUiState(
        overtimeHours = "",
        hasOvertimeHoursError = false,
        paidAbsenceDays = value,
        hasPaidAbsenceDaysError = false,
        sickDays = "",
        hasSickDaysError = false
      ),
      viewModel.uiState.value
    )
  }

  @Test
  fun `when absence days value is not integer then update state with error`() =
    runTest {
      //Given
      val viewModel = OverTimeInputViewModel(
        bonusSalaryRepositoryImpl = FakeBonusSalaryRepository(Treshold.HaveTreshold),
        navigator = navigationWrapper
      )
      //When
      val value = "not a number"
      viewModel.onUiEvent(OverTimeInputUiEvent.AbsenceDaysValueChanged(value))
      advanceUntilIdle()

      //Then
      assertEquals(
        OverTimeInputUiState(
          overtimeHours = "",
          hasOvertimeHoursError = false,
          paidAbsenceDays = value,
          hasPaidAbsenceDaysError = true,
          sickDays = "",
          hasSickDaysError = false
        ),
        viewModel.uiState.value
      )
    }

  @Test
  fun `when absence days value is changed and it's OVER 31 then update state with error`() =
    runTest {
      //Given
      val viewModel = OverTimeInputViewModel(
        bonusSalaryRepositoryImpl = FakeBonusSalaryRepository(Treshold.HaveTreshold),
        navigator = navigationWrapper
      )
      //When
      val value = "44"
      viewModel.onUiEvent(OverTimeInputUiEvent.AbsenceDaysValueChanged(value))
      advanceUntilIdle()

      //Then
      assertEquals(
        OverTimeInputUiState(
          overtimeHours = "",
          hasOvertimeHoursError = false,
          paidAbsenceDays = value,
          hasPaidAbsenceDaysError = true,
          sickDays = "",
          hasSickDaysError = false
        ),
        viewModel.uiState.value
      )
    }

  @Test
  fun `when absence days value is changed and it's EQUAL 31 then update state with error`() =
    runTest {
      //Given
      val viewModel = OverTimeInputViewModel(
        bonusSalaryRepositoryImpl = FakeBonusSalaryRepository(Treshold.HaveTreshold),
        navigator = navigationWrapper
      )
      //When
      val value = "31"
      viewModel.onUiEvent(OverTimeInputUiEvent.AbsenceDaysValueChanged(value))
      advanceUntilIdle()

      //Then
      assertEquals(
        OverTimeInputUiState(
          overtimeHours = "",
          hasOvertimeHoursError = false,
          paidAbsenceDays = value,
          hasPaidAbsenceDaysError = true,
          sickDays = "",
          hasSickDaysError = false
        ),
        viewModel.uiState.value
      )
    }

  @Test
  fun `when absence days value is changed and it's less than 0 then update state with error`() =
    runTest {
      //Given
      val viewModel = OverTimeInputViewModel(
        bonusSalaryRepositoryImpl = FakeBonusSalaryRepository(Treshold.HaveTreshold),
        navigator = navigationWrapper
      )
      //When
      val value = "-2"
      viewModel.onUiEvent(OverTimeInputUiEvent.AbsenceDaysValueChanged(value))
      advanceUntilIdle()

      //Then
      assertEquals(
        OverTimeInputUiState(
          overtimeHours = "",
          hasOvertimeHoursError = false,
          paidAbsenceDays = value,
          hasPaidAbsenceDaysError = true,
          sickDays = "",
          hasSickDaysError = false
        ),
        viewModel.uiState.value
      )
    }

  @Test
  fun `when overtime hours value is in valid range then update state`() = runTest {
    //Given
    val viewModel = OverTimeInputViewModel(
      bonusSalaryRepositoryImpl = FakeBonusSalaryRepository(Treshold.HaveTreshold),
      navigator = navigationWrapper
    )
    //When
    val value = "15"
    viewModel.onUiEvent(OverTimeInputUiEvent.OvertimeHoursValueChanged(value))
    advanceUntilIdle()

    //Then
    assertEquals(
      OverTimeInputUiState(
        overtimeHours = value,
        hasOvertimeHoursError = false,
        paidAbsenceDays = "",
        hasPaidAbsenceDaysError = false,
        sickDays = "",
        hasSickDaysError = false
      ),
      viewModel.uiState.value
    )
  }

  @Test
  fun `when overtime hours value is changed and it's OVER 100 then update state with error`() =
    runTest {
      //Given
      val viewModel = OverTimeInputViewModel(
        bonusSalaryRepositoryImpl = FakeBonusSalaryRepository(Treshold.HaveTreshold),
        navigator = navigationWrapper
      )
      //When
      val value = "101"
      viewModel.onUiEvent(OverTimeInputUiEvent.OvertimeHoursValueChanged(value))
      advanceUntilIdle()

      //Then
      assertEquals(
        OverTimeInputUiState(
          overtimeHours = value,
          hasOvertimeHoursError = true,
          paidAbsenceDays = "",
          hasPaidAbsenceDaysError = false,
          sickDays = "",
          hasSickDaysError = false
        ),
        viewModel.uiState.value
      )
    }

  @Test
  fun `when overtime hours value is not an integer then update state with error`() =
    runTest {
      //Given
      val viewModel = OverTimeInputViewModel(
        bonusSalaryRepositoryImpl = FakeBonusSalaryRepository(Treshold.HaveTreshold),
        navigator = navigationWrapper
      )
      //When
      val value = "not a number"
      viewModel.onUiEvent(OverTimeInputUiEvent.OvertimeHoursValueChanged(value))
      advanceUntilIdle()

      //Then
      assertEquals(
        OverTimeInputUiState(
          overtimeHours = value,
          hasOvertimeHoursError = true,
          paidAbsenceDays = "",
          hasPaidAbsenceDaysError = false,
          sickDays = "",
          hasSickDaysError = false
        ),
        viewModel.uiState.value
      )
    }

  @Test
  fun `when overtime hours value is changed and it's EQUAL 100 then update state with error`() =
    runTest {
      //Given
      val viewModel = OverTimeInputViewModel(
        bonusSalaryRepositoryImpl = FakeBonusSalaryRepository(Treshold.HaveTreshold),
        navigator = navigationWrapper
      )
      //When
      val value = "100"
      viewModel.onUiEvent(OverTimeInputUiEvent.OvertimeHoursValueChanged(value))
      advanceUntilIdle()

      //Then
      assertEquals(
        OverTimeInputUiState(
          overtimeHours = value,
          hasOvertimeHoursError = true,
          paidAbsenceDays = "",
          hasPaidAbsenceDaysError = false,
          sickDays = "",
          hasSickDaysError = false
        ),
        viewModel.uiState.value
      )
    }

  @Test
  fun `when overtime hours value is changed and it's less than 0 then update state with error`() =
    runTest {
      //Given
      val viewModel = OverTimeInputViewModel(
        bonusSalaryRepositoryImpl = FakeBonusSalaryRepository(Treshold.HaveTreshold),
        navigator = navigationWrapper
      )
      //When
      val value = "-2"
      viewModel.onUiEvent(OverTimeInputUiEvent.OvertimeHoursValueChanged(value))
      advanceUntilIdle()

      //Then
      assertEquals(
        OverTimeInputUiState(
          overtimeHours = value,
          hasOvertimeHoursError = true,
          paidAbsenceDays = "",
          hasPaidAbsenceDaysError = false,
          sickDays = "",
          hasSickDaysError = false
        ),
        viewModel.uiState.value
      )
    }

  @Test
  fun `when sick days value is in valid range then update state`() = runTest {
    //Given
    val viewModel = OverTimeInputViewModel(
      bonusSalaryRepositoryImpl = FakeBonusSalaryRepository(Treshold.HaveTreshold),
      navigator = navigationWrapper
    )
    //When
    val value = "12"
    viewModel.onUiEvent(OverTimeInputUiEvent.SickDaysValueChanged(value))
    advanceUntilIdle()

    //Then
    assertEquals(
      OverTimeInputUiState(
        overtimeHours = "",
        hasOvertimeHoursError = false,
        paidAbsenceDays = "",
        hasPaidAbsenceDaysError = false,
        sickDays = value,
        hasSickDaysError = false
      ),
      viewModel.uiState.value
    )
  }


  @Test
  fun `when sick days value is changed and it's OVER 31 then update state with error`() = runTest {
    //Given
    val viewModel = OverTimeInputViewModel(
      bonusSalaryRepositoryImpl = FakeBonusSalaryRepository(Treshold.HaveTreshold),
      navigator = navigationWrapper
    )
    //When
    val value = "55"
    viewModel.onUiEvent(OverTimeInputUiEvent.SickDaysValueChanged(value))
    advanceUntilIdle()

    //Then
    assertEquals(
      OverTimeInputUiState(
        overtimeHours = "",
        hasOvertimeHoursError = false,
        paidAbsenceDays = "",
        hasPaidAbsenceDaysError = false,
        sickDays = value,
        hasSickDaysError = true
      ),
      viewModel.uiState.value
    )
  }

  @Test
  fun `when sick days value is not an integer then update state with error`() = runTest {
    //Given
    val viewModel = OverTimeInputViewModel(
      bonusSalaryRepositoryImpl = FakeBonusSalaryRepository(Treshold.HaveTreshold),
      navigator = navigationWrapper
    )
    //When
    val value = "not a number"
    viewModel.onUiEvent(OverTimeInputUiEvent.SickDaysValueChanged(value))
    advanceUntilIdle()

    //Then
    assertEquals(
      OverTimeInputUiState(
        overtimeHours = "",
        hasOvertimeHoursError = false,
        paidAbsenceDays = "",
        hasPaidAbsenceDaysError = false,
        sickDays = value,
        hasSickDaysError = true
      ),
      viewModel.uiState.value
    )
  }

  @Test
  fun `when sick days value is changed and it's EQUAL 31 then update state with error`() = runTest {
    //Given
    val viewModel = OverTimeInputViewModel(
      bonusSalaryRepositoryImpl = FakeBonusSalaryRepository(Treshold.HaveTreshold),
      navigator = navigationWrapper
    )
    //When
    val value = "31"
    viewModel.onUiEvent(OverTimeInputUiEvent.SickDaysValueChanged(value))
    advanceUntilIdle()

    //Then
    assertEquals(
      OverTimeInputUiState(
        overtimeHours = "",
        hasOvertimeHoursError = false,
        paidAbsenceDays = "",
        hasPaidAbsenceDaysError = false,
        sickDays = value,
        hasSickDaysError = true
      ),
      viewModel.uiState.value
    )
  }

  @Test
  fun `when sick days value is changed and it's less than 0 then update state with error`() =
    runTest {
      //Given
      val viewModel = OverTimeInputViewModel(
        bonusSalaryRepositoryImpl = FakeBonusSalaryRepository(Treshold.HaveTreshold),
        navigator = navigationWrapper
      )
      //When
      val value = "-2"
      viewModel.onUiEvent(OverTimeInputUiEvent.SickDaysValueChanged(value))
      advanceUntilIdle()

      //Then
      assertEquals(
        OverTimeInputUiState(
          overtimeHours = "",
          hasOvertimeHoursError = false,
          paidAbsenceDays = "",
          hasPaidAbsenceDaysError = false,
          sickDays = value,
          hasSickDaysError = true
        ),
        viewModel.uiState.value
      )
    }

  @Test
  fun `when save is clicked then insert monthly stats and call navigate back`() = runTest {
    //Given
    val bonusSalaryRepository = mockk<BonusSalaryRepository>()
    val viewModel = OverTimeInputViewModel(
      bonusSalaryRepositoryImpl = bonusSalaryRepository,
      navigator = navigationWrapper
    )
    coEvery { bonusSalaryRepository.insertMonthlyStats(any()) } returns mockk()
    coEvery { navigationWrapper.navigateBack() } just Runs

    //When
    val value = "Јануари"
    viewModel.onUiEvent(OverTimeInputUiEvent.SaveClicked(value))
    advanceUntilIdle()

    //Then
    coVerify(exactly = 1) {
      bonusSalaryRepository.insertMonthlyStats(any())
      navigationWrapper.navigateBack()
    }
  }

  @Test
  fun `fetch monthly stats successfully and update UI state`() = runTest {
    //Given
    val bonusSalaryRepository = FakeBonusSalaryRepository(treshold = Treshold.HaveTreshold)
    val viewModel = OverTimeInputViewModel(
      bonusSalaryRepositoryImpl = bonusSalaryRepository,
      navigator = navigationWrapper
    )

    //When
    val value = "Јануари"
    viewModel.onUiEvent(OverTimeInputUiEvent.FetchMonthlyStats(value))
    advanceUntilIdle()

    //Then
    assertEquals(
      OverTimeInputUiState(
        overtimeHours = "10",
        hasOvertimeHoursError = false,
        paidAbsenceDays = "0",
        hasPaidAbsenceDaysError = false,
        sickDays = "0",
        hasSickDaysError = false
      ),
      viewModel.uiState.value
    )

  }

  @Test
  fun `when fetch monthly stats fails then get default ui state`() = runTest {
    //Given
    val bonusSalaryRepository = FakeBonusSalaryRepository(shouldFetchMonthlyStatsFail = true)
    val viewModel = OverTimeInputViewModel(
      bonusSalaryRepositoryImpl = bonusSalaryRepository,
      navigator = navigationWrapper
    )

    //When
    val value = "Јануари"
    viewModel.onUiEvent(OverTimeInputUiEvent.FetchMonthlyStats(value))
    advanceUntilIdle()

    //Then
    assertEquals(
      OverTimeInputUiState(
        overtimeHours = "0",
        hasOvertimeHoursError = false,
        paidAbsenceDays = "0",
        hasPaidAbsenceDaysError = false,
        sickDays = "0",
        hasSickDaysError = false
      ),
      viewModel.uiState.value
    )
  }
}