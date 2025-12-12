package com.boryanz.upszakoni.laws

import com.boryanz.upszakoni.MainDispatcherRule
import com.boryanz.upszakoni.domain.UpsError
import com.boryanz.upszakoni.domain.laws.model.Law
import com.boryanz.upszakoni.fakes.FakeRemoteConfigRepository
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import com.boryanz.upszakoni.ui.screens.common.UiState
import com.boryanz.upszakoni.ui.screens.laws.LawsEvent
import com.boryanz.upszakoni.ui.screens.laws.LawsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LawsViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private lateinit var viewmodel: LawsViewModel


  @Test
  fun `get laws successfully`() = runTest {
    //Given
    val expectedUiState = UiState(
      laws = listOf(
        Law(
          id = "public-peace",
          title = "Закон за прекршоци против ЈРМ"
        ),
        Law(
          id = "traffic-law",
          title = "Закон за возила"
        ),
        Law(
          id = "traffic-law",
          title = "Закон за безбедност во сообраќајот и патиштата"
        )
      )
    )
    viewmodel = LawsViewModel(
      remoteConfigRepository = FakeRemoteConfigRepository(),
      lawsRepository = FakeLawsRepository(expectedUiState.laws),
    )
    //When
    viewmodel.onUiEvent(ScreenAction.GetLaws)

    //Then
    assertEquals(expectedUiState, viewmodel.uiState.value)
  }


  @Test
  fun `get laws failed with No connection error`() = runTest(StandardTestDispatcher()) {
    //Given
    val expectedLaws = listOf(
      Law(
        id = "public-peace",
        title = "Закон за прекршоци против ЈРМ"
      ),
      Law(
        id = "traffic-law",
        title = "Закон за возила"
      ),
      Law(
        id = "traffic-law",
        title = "Закон за безбедност во сообраќајот и патиштата"
      )
    )
    viewmodel = LawsViewModel(
      remoteConfigRepository = FakeRemoteConfigRepository(),
      lawsRepository = FakeLawsRepository(expectedLaws).apply {
        failWithNoConnectionError = true
      },
    )

    //When
    val result = collectFlow(viewmodel.event) {
      viewmodel.onUiEvent(ScreenAction.GetLaws)
      advanceUntilIdle()
    }

    assertTrue(result.first() is LawsEvent.Failure)
    assertEquals(UpsError.NoInternetConnectionError, (result.first() as LawsEvent.Failure).error)

    //Then
    assertTrue(viewmodel.uiState.value.laws.isEmpty())

  }

  @Test
  fun `get laws successfully does not emit events`() = runTest {
    //Given
    val expectedLaws = listOf(
      Law(
        id = "public-peace",
        title = "Закон за прекршоци против ЈРМ"
      ),
      Law(
        id = "traffic-law",
        title = "Закон за возила"
      )
    )
    viewmodel = LawsViewModel(
      remoteConfigRepository = FakeRemoteConfigRepository(),
      lawsRepository = FakeLawsRepository(expectedLaws),
    )

    val result = collectFlow(viewmodel.event) {
      viewmodel.onUiEvent(ScreenAction.GetLaws)
    }

    //When
    viewmodel.onUiEvent(ScreenAction.GetLaws)

    //Then
    assertEquals(expectedLaws, viewmodel.uiState.value.laws)
    assertTrue(result.isEmpty())

  }

  @Test
  fun `law download success`() = runTest {
    val expectedFilePath = "/data/data/com.boryanz.upszakoni/files/downloaded.pdf"
    val expectedLaws = listOf(
      Law(
        id = "public-peace",
        title = "Закон за прекршоци против ЈРМ"
      ),
      Law(
        id = "traffic-law",
        title = "Закон за возила"
      )
    )

    viewmodel = LawsViewModel(
      remoteConfigRepository = FakeRemoteConfigRepository(),
      lawsRepository = FakeLawsRepository(expectedLaws).apply {
        downloadedFileName = expectedFilePath
      },
    )

    val result = collectFlow(viewmodel.event) {
      viewmodel.onUiEvent(
        ScreenAction.LawClicked(
          fileName = expectedLaws.first().title,
          id = expectedLaws.first().id
        )
      )
    }

    assertEquals(LawsEvent.PdfReady(expectedFilePath), result.first())
  }

  @Test
  fun `law download failure`() = runTest {
    val expectedLaws = listOf(
      Law(
        id = "public-peace",
        title = "Закон за прекршоци против ЈРМ"
      ),
      Law(
        id = "traffic-law",
        title = "Закон за возила"
      )
    )

    viewmodel = LawsViewModel(
      remoteConfigRepository = FakeRemoteConfigRepository(),
      lawsRepository = FakeLawsRepository(expectedLaws),
    )

    val result = collectFlow(viewmodel.event) {
      viewmodel.onUiEvent(
        ScreenAction.LawClicked(
          fileName = expectedLaws.first().title,
          id = expectedLaws.first().id
        )
      )
    }

    assertEquals(LawsEvent.Failure(UpsError.GeneralError), result.first())
  }
}

fun <T> TestScope.collectFlow(sharedFlow: SharedFlow<T>, block: () -> Unit): List<T> {
  val result = arrayListOf<T>()
  val job = launch(Dispatchers.Unconfined) {
    sharedFlow.toCollection(result)
  }
  block()
  job.cancel()
  return result
}


