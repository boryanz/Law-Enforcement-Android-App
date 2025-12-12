package com.boryanz.upszakoni.ai

import com.boryanz.upszakoni.MainDispatcherRule
import com.boryanz.upszakoni.fakes.FakeDocumentsDocumentsDao
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryUiState
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryUserEvent
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryViewModel
import com.boryanz.upszakoni.ui.screens.ai.history.GeneratedDocument
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DocumentHistoryViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val document = GeneratedDocument(
    id = 1,
    title = "ЈРМ",
    content = "Учество во тепачка во толпа...",
    generatedDate = "21.11.2025"
  )

  val documents = listOf(
    document,
    document.copy(id = 2, title = "Поплака", content = "Гласна музика во стан."),
    document.copy(id = 2, title = "Обезбедување место на настан", content = "Тешка кражба"),
  )

  @Test
  fun `should have default ui state when viewmodel is initialized`() = runTest {
    //Given
    val expectedUiState = DocumentHistoryUiState(
      isLoading = false,
      generatedDocuments = emptyList(),
      documentToDelete = null
    )
    val fakeDao = FakeDocumentsDocumentsDao(emptyList())
    val viewModel = DocumentHistoryViewModel(fakeDao)
    //Then
    assertEquals(expectedUiState, viewModel.uiState.value)
  }

  @Test
  fun `show alert dialog when user clicks delete icon`() = runTest {
    val document = GeneratedDocument(
      id = 1,
      title = "ЈРМ",
      content = "Учество во тепачка во толпа...",
      generatedDate = "21.11.2025"
    )
    val expectedUiState = DocumentHistoryUiState(
      isLoading = false,
      generatedDocuments = emptyList(),
      documentToDelete = document
    )
    val fakeDao = FakeDocumentsDocumentsDao(emptyList())
    val viewModel = DocumentHistoryViewModel(fakeDao)
    viewModel.onUserEvent(DocumentHistoryUserEvent.DocumentDeleteClicked(document))

    assertEquals(expectedUiState, viewModel.uiState.value)
  }

  @Test
  fun `hide alert dialog when user dismiss it`() = runTest {
    val expectedUiState = DocumentHistoryUiState(
      isLoading = false,
      generatedDocuments = emptyList(),
      documentToDelete = null
    )

    val fakeDao = FakeDocumentsDocumentsDao(emptyList())
    val viewModel = DocumentHistoryViewModel(fakeDao)
    viewModel.onUserEvent(DocumentHistoryUserEvent.DeleteDialogDismissed)

    assertEquals(expectedUiState, viewModel.uiState.value)
  }

  @Test
  fun `delete document when user confirms alert dialog`() = runTest {
    val expectedUiState = DocumentHistoryUiState(
      isLoading = false,
      generatedDocuments = emptyList(),
      documentToDelete = null
    )
    val fakeDao = FakeDocumentsDocumentsDao(emptyList())
    val viewModel = DocumentHistoryViewModel(fakeDao)

    viewModel.onUserEvent(DocumentHistoryUserEvent.DeleteDialogConfirmed)

    assertEquals(expectedUiState, viewModel.uiState.value)
  }

  @Test
  fun `get all available documents from dao`() = runTest {
    val expectedUiState = DocumentHistoryUiState(
      isLoading = false,
      generatedDocuments = documents,
      documentToDelete = null
    )
    val fakeDao = FakeDocumentsDocumentsDao(documents)
    val viewModel = DocumentHistoryViewModel(fakeDao)

    viewModel.onUserEvent(DocumentHistoryUserEvent.OnCreate)

    assertEquals(expectedUiState, viewModel.uiState.value)
  }
}