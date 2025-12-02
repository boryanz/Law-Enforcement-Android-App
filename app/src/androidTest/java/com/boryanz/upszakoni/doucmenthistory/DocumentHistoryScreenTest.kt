package com.boryanz.upszakoni.doucmenthistory

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryContent
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryUiState
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryUserEvent
import com.boryanz.upszakoni.ui.screens.ai.history.GeneratedDocument
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DocumentHistoryScreenTest {

  @get:Rule
  val rule = createAndroidComposeRule<ComponentActivity>()

  private fun setupScreen(
    uiState: DocumentHistoryUiState = DocumentHistoryUiState(),
    onFabClicked: (String) -> Unit = {},
  ) {
    var uiState by mutableStateOf(uiState)

    rule.setContent {
      DocumentHistoryContent(
        uiState = uiState,
        onBackClicked = {},
        onDocumentClicked = {},
        onUserEvent = { event ->
          when (event) {
            DocumentHistoryUserEvent.DeleteDialogConfirmed -> TODO()
            DocumentHistoryUserEvent.DeleteDialogDismissed -> TODO()
            is DocumentHistoryUserEvent.DocumentDeleteClicked -> {}
            DocumentHistoryUserEvent.OnCreate -> {}
            DocumentHistoryUserEvent.FABClicked -> onFabClicked("FAB_clicked")
          }
        }
      )
    }
  }

  @Test
  fun testShouldShowLoadingWhenNoDocumentsAreAvailable() {
    setupScreen(DocumentHistoryUiState(isLoading = true))
    rule.onNodeWithTag("loader").assertExists()
    rule.onNodeWithContentDescription("generate_document_fab").assertDoesNotExist()
    rule.onNodeWithContentDescription("back_button").assertExists()
  }

  @Test
  fun showNoGenerateDocumentsWhenNothingIsFetchedFromLocalDb() {
    setupScreen()
    rule.onNodeWithContentDescription("back_button").assertExists()
    /* Loading state is not shown */
    rule.onNodeWithTag("loader").assertDoesNotExist()

    /* When list is empty */
    rule.onNodeWithText(rule.activity.getString(R.string.no_ai_generated_documents_info_text))
      .assertExists()
  }

  @Test
  fun whenUserClickFloatingActionButtonThenNavigateNext() {
    val infoText = rule.activity.getString(R.string.no_ai_generated_documents_info_text)
    var fabClick = ""
    setupScreen(onFabClicked = { fabClick = it })
    rule.onNodeWithContentDescription("back_button").assertExists()
    /* Loading state is not shown */
    rule.onNodeWithTag("loader").assertDoesNotExist()

    /* When list is empty */
    rule.onNodeWithText(infoText).assertExists()
    rule.onNodeWithContentDescription("generate_document_fab").assertExists()
    rule.onNodeWithContentDescription("generate_document_fab").performClick()
    rule.waitForIdle()

    assertEquals("FAB_clicked", fabClick)
  }

  @Test
  fun whenDocumentsAreFetchedThenShowThem() {
    val documents = listOf(
      GeneratedDocument(
        id = 1,
        title = "ЈРМ",
        content = "Нарушување на ЈРМ со тепачка во толпа...",
        generatedDate = "30.11.2025"
      ),
    )
    setupScreen(uiState = DocumentHistoryUiState(generatedDocuments = documents))
    rule.onNodeWithContentDescription("back_button").assertExists()
    /* Loading state is not shown */
    rule.onNodeWithTag("loader").assertDoesNotExist()

    rule.onNodeWithContentDescription("delete_ai_document_button").assertExists()
    rule.onNodeWithText("ЈРМ").assertExists()
    rule.onNodeWithText("30.11.2025").assertExists()
    rule.onNodeWithText("Нарушување на ЈРМ со тепачка во толпа...").assertExists()
  }
}