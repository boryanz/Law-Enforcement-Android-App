package com.boryanz.upszakoni.bonussalary

import com.boryanz.upszakoni.MainDispatcherRule
import com.boryanz.upszakoni.fakes.FakeBonusSalaryRepository
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersUiEvent
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersUiState
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BonusSalaryParametersViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BonusSalaryParametersViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()


  @Test
  fun `default UI state should have values for overtime limit and absence limit`() = runTest {
    //Given
    val viewmodel = BonusSalaryParametersViewModel(FakeBonusSalaryRepository())

    //Then
    assertEquals(
      BonusSalaryParametersUiState(
        overtimeLimitValue = "150",
        absenceDaysLimitValue = "21",
        hasOvertimeLimitError = false,
        hasAbsenceDaysLimitError = false
      ),
      viewmodel.uiState.value
    )
  }

  @Test
  fun `when absence limit changed and value is less than ZERO then update ui state WITH ERROR`() =
    runTest {
      //Given
      val viewmodel = BonusSalaryParametersViewModel(FakeBonusSalaryRepository())

      //When
      val value = "-3"
      viewmodel.onUiEvent(BonusSalaryParametersUiEvent.AbsenceLimitChanged(value))

      //Then
      assertEquals(
        BonusSalaryParametersUiState(
          overtimeLimitValue = "150",
          absenceDaysLimitValue = value,
          hasOvertimeLimitError = false,
          hasAbsenceDaysLimitError = true
        ),
        viewmodel.uiState.value
      )
    }

  @Test
  fun `when absence limit changed and value is EQUAL ZERO then update ui state WITH ERROR`() =
    runTest {
      //Given
      val viewmodel = BonusSalaryParametersViewModel(FakeBonusSalaryRepository())

      //When
      val value = "0"
      viewmodel.onUiEvent(BonusSalaryParametersUiEvent.AbsenceLimitChanged(value))

      //Then
      assertEquals(
        BonusSalaryParametersUiState(
          overtimeLimitValue = "150",
          absenceDaysLimitValue = value,
          hasOvertimeLimitError = false,
          hasAbsenceDaysLimitError = true
        ),
        viewmodel.uiState.value
      )
    }

  @Test
  fun `when absence limit changed and value is in valid range then update ui state`() = runTest {
    //Given
    val viewmodel = BonusSalaryParametersViewModel(FakeBonusSalaryRepository())

    //When
    val value = "10"
    viewmodel.onUiEvent(BonusSalaryParametersUiEvent.AbsenceLimitChanged(value))

    //Then
    assertEquals(
      BonusSalaryParametersUiState(
        overtimeLimitValue = "150",
        absenceDaysLimitValue = value,
        hasOvertimeLimitError = false,
        hasAbsenceDaysLimitError = false
      ),
      viewmodel.uiState.value
    )
  }

  @Test
  fun `when overtime limit changed and value is less than 100 then update ui state WITH ERROR`() =
    runTest {
      //Given
      val viewmodel = BonusSalaryParametersViewModel(FakeBonusSalaryRepository())

      //When
      val value = "50"
      viewmodel.onUiEvent(BonusSalaryParametersUiEvent.OvertimeLimitChanged(value))

      //Then
      assertEquals(
        BonusSalaryParametersUiState(
          overtimeLimitValue = value,
          absenceDaysLimitValue = "21",
          hasOvertimeLimitError = true,
          hasAbsenceDaysLimitError = false
        ),
        viewmodel.uiState.value
      )
    }

  @Test
  fun `when overtime limit changed and value is in valid range then update ui state`() = runTest {
    //Given
    val viewmodel = BonusSalaryParametersViewModel(FakeBonusSalaryRepository())

    //When
    val value = "101"
    viewmodel.onUiEvent(BonusSalaryParametersUiEvent.OvertimeLimitChanged(value))

    //Then
    assertEquals(
      BonusSalaryParametersUiState(
        overtimeLimitValue = value,
        absenceDaysLimitValue = "21",
        hasOvertimeLimitError = false,
        hasAbsenceDaysLimitError = false
      ),
      viewmodel.uiState.value
    )
  }

  @Test
  fun `fetch data successfully and update the ui state`() = runTest {
    //Given
    val viewmodel = BonusSalaryParametersViewModel(FakeBonusSalaryRepository())

    //When
    viewmodel.onUiEvent(BonusSalaryParametersUiEvent.FetchData)

    //Then
    assertEquals(
      BonusSalaryParametersUiState(
        overtimeLimitValue = "150",
        absenceDaysLimitValue = "21",
        hasOvertimeLimitError = false,
        hasAbsenceDaysLimitError = false
      ),
      viewmodel.uiState.value
    )
  }
}