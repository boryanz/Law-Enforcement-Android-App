package com.boryanz.upszakoni

import com.boryanz.upszakoni.fakes.FakeBonusSalaryRepository
import com.boryanz.upszakoni.fakes.Treshold.Error
import com.boryanz.upszakoni.fakes.Treshold.HaveTreshold
import com.boryanz.upszakoni.fakes.Treshold.NoTreshold
import com.boryanz.upszakoni.ui.navigation.navgraph.overtimetracking.OvertimeTrackNavigationGraphViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OverTimeTrackingNavigationGraphViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private lateinit var viewmodel: OvertimeTrackNavigationGraphViewModel

  @Test
  fun `treshold is true when there is initial data`() = runTest {
    //Given
    viewmodel =
      OvertimeTrackNavigationGraphViewModel(FakeBonusSalaryRepository(treshold = HaveTreshold))
    assertEquals(null, viewmodel.hasTresholdSet.value)
    advanceUntilIdle()
    assertEquals(true, viewmodel.hasTresholdSet.value)
  }

  @Test
  fun `treshold state is false when there is no initial data`() = runTest {
    //Given
    viewmodel =
      OvertimeTrackNavigationGraphViewModel(FakeBonusSalaryRepository(treshold = NoTreshold))
    //Then
    assertEquals(null, viewmodel.hasTresholdSet.value)
    advanceUntilIdle()
    assertEquals(false, viewmodel.hasTresholdSet.value)
  }

  @Test
  fun `tresHold state is false when error occur`() = runTest {
    //Given
    viewmodel =
      OvertimeTrackNavigationGraphViewModel(FakeBonusSalaryRepository(Error))
    //Then
    assertEquals(null, viewmodel.hasTresholdSet.value)
    advanceUntilIdle()
    assertEquals(false, viewmodel.hasTresholdSet.value)
  }
}
