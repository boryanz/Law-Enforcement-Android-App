package com.boryanz.upszakoni

import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsDao
import com.boryanz.upszakoni.fakes.FakeLawsUseCase
import com.boryanz.upszakoni.ui.screens.archivedlaws.ArchivedLawsViewModel
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import com.boryanz.upszakoni.ui.screens.common.UiState
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockkObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArchivedViewModelTest {

  @get:Rule
  val mockkRule = MockKRule(this)

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private lateinit var viewmodel: ArchivedLawsViewModel

  @Before
  fun setup() {
    mockkObject(SharedPrefsDao)
    viewmodel = ArchivedLawsViewModel(FakeLawsUseCase())
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `get laws successfully`() = runTest {
    //Given
    val expectedUiState =
      UiState(listOf("закон за прекшоци", "закон за возила", "закон за странците"))
    coEvery { SharedPrefsDao.contains(any()) } returns true

    //When
    viewmodel.onUiEvent(ScreenAction.GetLaws)

    //Then
    assertEquals(expectedUiState, viewmodel.uiState.value)
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `remove archived law successfully`() = runTest {
    //Given
    val expectedAfterSwipe = UiState(listOf("закон за возила", "закон за странците"))
    val swipedLaw = "закон за прекшоци"

    coEvery { SharedPrefsDao.contains("закон за прекшоци") } returns true
    coEvery { SharedPrefsDao.contains("закон за возила") } returns true
    coEvery { SharedPrefsDao.contains("закон за странците") } returns true
    coEvery { SharedPrefsDao.removeArchivedLaw(swipedLaw) } returns Unit

    //When
    viewmodel.onUiEvent(ScreenAction.GetLaws)

    //Then
    assertEquals(
      UiState(listOf("закон за прекшоци", "закон за возила", "закон за странците")),
      viewmodel.uiState.value
    )

    //Given
    coEvery { SharedPrefsDao.contains(swipedLaw) } returns false

    //When
    viewmodel.onUiEvent(ScreenAction.LawSwiped(swipedLaw))

    //Then
    assertEquals(expectedAfterSwipe, viewmodel.uiState.value)
  }
}

