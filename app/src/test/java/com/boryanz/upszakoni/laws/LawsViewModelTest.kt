package com.boryanz.upszakoni.laws

import com.boryanz.upszakoni.MainDispatcherRule
import com.boryanz.upszakoni.fakes.FakeLawsUseCase
import com.boryanz.upszakoni.fakes.FakePrefsLocalStorage
import com.boryanz.upszakoni.fakes.FakeRemoteConfigRepository
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import com.boryanz.upszakoni.ui.screens.common.UiState
import com.boryanz.upszakoni.ui.screens.laws.LawsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class LawsViewModelTest {

  @OptIn(ExperimentalCoroutinesApi::class)
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private lateinit var viewmodel: LawsViewModel


  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `get laws successfully`() = runTest {
    //Given
    viewmodel = LawsViewModel(
      getLawsUseCase = FakeLawsUseCase(),
      remoteConfigRepository = FakeRemoteConfigRepository(),
      localStorage = FakePrefsLocalStorage(data = mutableMapOf())
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
  fun `archive law successfully`() = runTest {
    //Given
    viewmodel = LawsViewModel(
      getLawsUseCase = FakeLawsUseCase(),
      remoteConfigRepository = FakeRemoteConfigRepository(),
      localStorage = FakePrefsLocalStorage(
        data = mutableMapOf(
          "archive/закон за прекршоци" to true
        )
      )
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


