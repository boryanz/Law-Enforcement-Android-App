package com.boryanz.upszakoni

import android.content.Context
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsDao
import com.boryanz.upszakoni.domain.GetLawsUseCase
import com.boryanz.upszakoni.ui.screens.archivedlaws.ArchivedLawsViewModel
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import com.boryanz.upszakoni.ui.screens.common.UiState
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
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

  @MockK
  private lateinit var getLawsUseCase: GetLawsUseCase

  @Before
  fun setup() {
    mockkObject(SharedPrefsDao)
    viewmodel = ArchivedLawsViewModel(getLawsUseCase)
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `get laws successfully`() = runTest {
    val context = mockk<Context>()
    val expectedUiState =
      UiState(listOf("закон за прекшоци", "закон за возила", "закон за странците"))
    val laws = listOf("закон за прекшоци.pdf", "закон за возила.pdf", "закон за странците.pdf")
    coEvery { getLawsUseCase(context) } returns laws
    coEvery { SharedPrefsDao.contains(any()) } returns true


    viewmodel.onUiEvent(ScreenAction.GetLaws(context))
    advanceUntilIdle()

    assertEquals(expectedUiState, viewmodel.uiState.value)
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `remove archived law successfully`() = runTest {
    val context = mockk<Context>()

    val initialLaws = listOf(
      "закон за прекшоци.pdf",
      "закон за возила.pdf",
      "закон за странците.pdf"
    )

    val expectedAfterSwipe = UiState(listOf("закон за возила", "закон за странците"))
    val swipedLaw = "закон за прекшоци"

    coEvery { getLawsUseCase(context) } returns initialLaws

    coEvery { SharedPrefsDao.contains("закон за прекшоци") } returns true
    coEvery { SharedPrefsDao.contains("закон за возила") } returns true
    coEvery { SharedPrefsDao.contains("закон за странците") } returns true

    coEvery { SharedPrefsDao.removeArchivedLaw(swipedLaw) } returns Unit

    viewmodel.onUiEvent(ScreenAction.GetLaws(context))
    advanceUntilIdle()

    assertEquals(
      UiState(listOf("закон за прекшоци", "закон за возила", "закон за странците")),
      viewmodel.uiState.value
    )

    coEvery { SharedPrefsDao.contains(swipedLaw) } returns false

    viewmodel.onUiEvent(ScreenAction.LawSwiped(context, swipedLaw))
    advanceUntilIdle()

    assertEquals(expectedAfterSwipe, viewmodel.uiState.value)
  }

}

