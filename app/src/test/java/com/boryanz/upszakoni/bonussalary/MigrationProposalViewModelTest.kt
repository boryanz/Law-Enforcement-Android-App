package com.boryanz.upszakoni.bonussalary

import com.boryanz.upszakoni.MainDispatcherRule
import com.boryanz.upszakoni.fakes.FakeBonusSalaryRepository
import com.boryanz.upszakoni.fakes.FakeGenerateDaysInMonthsUseCase
import com.boryanz.upszakoni.fakes.FakePrefsLocalStorage
import com.boryanz.upszakoni.fakes.Treshold
import com.boryanz.upszakoni.ui.navigation.destinations.BonusSalaryDashboardDestination
import com.boryanz.upszakoni.ui.navigation.destinations.ParametersDestination
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.BonusSalaryUiState.DashboardDestination
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.BonusSalaryUiState.DefaultDestination
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.BonusSalaryUiState.ScreenContent
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.MigrationProposalViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MigrationProposalViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private lateinit var viewModel: MigrationProposalViewModel


  @Test
  fun `user rejected overtime migration`() = runTest {
    //Given
    viewModel = MigrationProposalViewModel(
      bonusSalaryRepository = FakeBonusSalaryRepository(),
      generateDefaultDaysInMonthsUseCase = FakeGenerateDaysInMonthsUseCase(),
      localStorage = FakePrefsLocalStorage(data = mutableMapOf())
    )
    //When
    viewModel.checkIfUserAlreadyHaveData()

    //Then
    assertEquals(ScreenContent, viewModel.uiState.value)
  }

  @Test
  fun `set DashboardDestination state when treshold values from repository are not null`() =
    runTest {
      //Given
      viewModel = MigrationProposalViewModel(
        bonusSalaryRepository = FakeBonusSalaryRepository(),
        generateDefaultDaysInMonthsUseCase = FakeGenerateDaysInMonthsUseCase(),
        localStorage = FakePrefsLocalStorage(
          data = mutableMapOf(
            "overtimeMigrationReject" to true
          )
        )
      )

      //When
      viewModel.checkIfUserAlreadyHaveData()

      //Then
      assertEquals(
        DashboardDestination(BonusSalaryDashboardDestination),
        viewModel.uiState.value
      )
    }

  @Test
  fun `set DefaultDestination state when treshold values from repository are null`() = runTest {
    //Given
    viewModel = MigrationProposalViewModel(
      bonusSalaryRepository = FakeBonusSalaryRepository(treshold = Treshold.NoTreshold),
      generateDefaultDaysInMonthsUseCase = FakeGenerateDaysInMonthsUseCase(),
      localStorage = FakePrefsLocalStorage(data = mutableMapOf("overtimeMigrationReject" to true))
    )

    //When
    viewModel.checkIfUserAlreadyHaveData()

    //Then
    assertEquals(
      DefaultDestination(startDestination = ParametersDestination),
      viewModel.uiState.value
    )
  }

  @Test
  fun `set DefaultDestination state when error occurs from repository`() = runTest {
    //Given
    viewModel = MigrationProposalViewModel(
      bonusSalaryRepository = FakeBonusSalaryRepository(Treshold.Error),
      generateDefaultDaysInMonthsUseCase = FakeGenerateDaysInMonthsUseCase(),
      localStorage = FakePrefsLocalStorage(data = mutableMapOf("overtimeMigrationReject" to true))
    )

    //When
    viewModel.checkIfUserAlreadyHaveData()

    //Then
    assertEquals(
      DefaultDestination(startDestination = ParametersDestination),
      viewModel.uiState.value
    )
  }
}