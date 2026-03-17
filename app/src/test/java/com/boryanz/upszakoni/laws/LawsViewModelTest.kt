package com.boryanz.upszakoni.laws

import com.boryanz.upszakoni.MainDispatcherRule
import com.boryanz.upszakoni.fakes.FakeAnalyticsManager
import com.boryanz.upszakoni.fakes.FakeLawsUseCase
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import com.boryanz.upszakoni.ui.screens.common.UiState
import com.boryanz.upszakoni.ui.screens.laws.LawsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
      analyticsLogger = FakeAnalyticsManager()
    )
    val expectedUiState =
      UiState(listOf("закон за прекшоци", "закон за возила", "закон за странците"))

    //When
    viewmodel.onUiEvent(ScreenAction.GetLaws)

    //Then
    assertEquals(expectedUiState, viewmodel.uiState.value)
  }
}


