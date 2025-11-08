//package com.boryanz.upszakoni.bonussalary
//
//import com.boryanz.upszakoni.MainDispatcherRule
//import com.boryanz.upszakoni.data.local.database.model.BonusSalaryTreshold
//import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsStorage
//import com.boryanz.upszakoni.fakes.FakeBonusSalaryRepository
//import com.boryanz.upszakoni.fakes.FakePrefsLocalStorage
//import com.boryanz.upszakoni.ui.navigation.destinations.BonusSalaryDashboardDestination
//import com.boryanz.upszakoni.ui.navigation.destinations.ParametersDestination
//import com.boryanz.upszakoni.ui.screens.bonussalary.migration.BonusSalaryGraphUiAction
//import com.boryanz.upszakoni.ui.screens.bonussalary.migration.BonusSalaryUiState.DashboardDestination
//import com.boryanz.upszakoni.ui.screens.bonussalary.migration.BonusSalaryUiState.DefaultDestination
//import com.boryanz.upszakoni.ui.screens.bonussalary.migration.BonusSalaryUiState.ScreenContent
//import com.boryanz.upszakoni.ui.screens.bonussalary.migration.MigrationProposalViewModel
//import io.mockk.Runs
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.just
//import io.mockk.mockkObject
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.advanceUntilIdle
//import kotlinx.coroutines.test.runTest
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class MigrationProposalViewModelTest {
//
//  @get:Rule
//  val mainDispatcherRule = MainDispatcherRule()
//
//  private lateinit var viewModel: MigrationProposalViewModel
//
//  @Before
//  fun setup() {
//    mockkObject(SharedPrefsStorage)
//    viewModel = MigrationProposalViewModel(
//      bonusSalaryRepository = FakeBonusSalaryRepository(),
//      localStorage = FakePrefsLocalStorage()
//    )
//  }
//
//  @Test
//  fun `test should verify that repository deletes all the data and store acceptance of migration in shared preferences`() =
//    runTest {
//      //Given
//      coEvery { bonusSalaryRepository.deleteAllAndGenerateDefaultData() } just Runs
//      coEvery { SharedPrefsStorage.acceptOvertimeTrackingMigration() } just Runs
//
//      //When
//      viewModel.onMigrationAccepted(BonusSalaryGraphUiAction.MigrationAccepted({}))
//      advanceUntilIdle()
//
//      //Then
//      coVerify(exactly = 1) {
//        bonusSalaryRepository.deleteAllAndGenerateDefaultData()
//        SharedPrefsStorage.acceptOvertimeTrackingMigration()
//      }
//    }
//
//  @Test
//  fun `user rejected overtime migration`() = runTest {
//    //Given
//    coEvery { SharedPrefsStorage.hasUserRejectedOvertimeTrackingMigration() } returns false
//
//    //When
//    viewModel.checkIfUserAlreadyHaveData()
//    advanceUntilIdle()
//
//    //Then
//    assertEquals(ScreenContent, viewModel.uiState.value)
//    coVerify(exactly = 1) {
//      SharedPrefsStorage.hasUserRejectedOvertimeTrackingMigration()
//    }
//  }
//
//  @Test
//  fun `set DashboardDestination state when treshold values from repository are not null`() =
//    runTest {
//      //Given
//      coEvery { SharedPrefsStorage.hasUserRejectedOvertimeTrackingMigration() } returns true
//      coEvery { bonusSalaryRepository.getTreshold(any()) } returns Result.success(
//        BonusSalaryTreshold(
//          id = "bonus_salary_treshold",
//          minimumOvertimeHours = "150",
//          maximumAbsenceDays = "21"
//        )
//      )
//
//      //When
//      viewModel.checkIfUserAlreadyHaveData()
//      advanceUntilIdle()
//
//      //Then
//      assertEquals(
//        DashboardDestination(BonusSalaryDashboardDestination),
//        viewModel.uiState.value
//      )
//      coVerify(exactly = 1) { bonusSalaryRepository.getTreshold(any()) }
//    }
//
//  @Test
//  fun `set DefaultDestination state when treshold values from repository are null`() = runTest {
//    //Given
//    coEvery { SharedPrefsStorage.hasUserRejectedOvertimeTrackingMigration() } returns true
//    coEvery { bonusSalaryRepository.getTreshold(any()) } returns Result.success(null)
//
//    //When
//    viewModel.checkIfUserAlreadyHaveData()
//    advanceUntilIdle()
//
//    //Then
//    assertEquals(
//      DefaultDestination(startDestination = ParametersDestination),
//      viewModel.uiState.value
//    )
//    coVerify(exactly = 1) { bonusSalaryRepository.getTreshold(any()) }
//  }
//
//  @Test
//  fun `set DefaultDestination state when error occurs from repository`() = runTest {
//    //Given
//    coEvery { SharedPrefsStorage.hasUserRejectedOvertimeTrackingMigration() } returns true
//    coEvery { bonusSalaryRepository.getTreshold(any()) } returns Result.failure(Exception())
//
//    //When
//    viewModel.checkIfUserAlreadyHaveData()
//    advanceUntilIdle()
//
//    //Then
//    assertEquals(
//      DefaultDestination(startDestination = ParametersDestination),
//      viewModel.uiState.value
//    )
//    coVerify(exactly = 1) { bonusSalaryRepository.getTreshold(any()) }
//  }
//}