package com.boryanz.upszakoni.laws

import com.boryanz.upszakoni.MainDispatcherRule
import com.boryanz.upszakoni.fakes.FakeAnalyticsManager
import com.boryanz.upszakoni.fakes.FakeLawsUseCase
import com.boryanz.upszakoni.fakes.FakePrefsLocalStorage
import com.boryanz.upszakoni.ui.screens.archivedlaws.ArchivedLawsViewModel
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import com.boryanz.upszakoni.ui.screens.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ArchivedViewModelTest {

  @OptIn(ExperimentalCoroutinesApi::class)
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()


  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `get laws successfully`() = runTest {
    //Given
    val fakeLocalStorage = FakePrefsLocalStorage(
      data = mutableMapOf(
        "archive/закон за прекшоци" to true,
        "archive/закон за возила" to true,
        "archive/закон за странците" to true,
      )
    )
    val viewmodel = ArchivedLawsViewModel(
      getLawsUseCase = FakeLawsUseCase(),
      localStorage = fakeLocalStorage,
      analyticsLogger = FakeAnalyticsManager()
    )
    val expectedUiState =
      UiState(listOf("закон за прекшоци", "закон за возила", "закон за странците"))

    //When
    viewmodel.onUiEvent(ScreenAction.GetLaws)

    //Then
    assertEquals(expectedUiState, viewmodel.uiState.value)
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `remove archived law successfully`() = runTest {
    //Given
    val fakeLocalStorage = FakePrefsLocalStorage(
      data = mutableMapOf(
        "archive/закон за прекшоци" to true,
        "archive/закон за возила" to true,
        "archive/закон за странците" to true,
      )
    )
    val viewmodel = ArchivedLawsViewModel(
      getLawsUseCase = FakeLawsUseCase(),
      localStorage = fakeLocalStorage,
      analyticsLogger = FakeAnalyticsManager()
    )
    val expectedAfterSwipe = UiState(listOf("закон за возила", "закон за странците"))
    val swipedLaw = "закон за прекшоци"

    //When
    viewmodel.onUiEvent(ScreenAction.GetLaws)
    viewmodel.onUiEvent(ScreenAction.LawSwiped(swipedLaw))

    //Then
    assertEquals(expectedAfterSwipe, viewmodel.uiState.value)
  }
}

