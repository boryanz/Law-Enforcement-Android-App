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
import org.junit.Rule
import org.junit.Test

class DocumentHistoryScreenTest {

  @get:Rule
  val rule = createAndroidComposeRule<ComponentActivity>()

  val documents = listOf(
    GeneratedDocument(
      id = 1,
      title = "ЈРМ",
      content = "Нарушување на ЈРМ со тепачка во толпа...",
      generatedDate = "30.11.2025"
    ),
  )

  private fun setupScreen(uiState: DocumentHistoryUiState = DocumentHistoryUiState()) {
    var uiState by mutableStateOf(uiState)

    rule.setContent {
      DocumentHistoryContent(
        uiState = uiState,
        onBackClicked = {},
        onAddDocumentClicked = {},
        onDocumentClicked = {},
        onUserEvent = { event ->
          when (event) {
            DocumentHistoryUserEvent.DeleteDialogConfirmed -> {
              uiState = uiState.copy(documentToDelete = null)
            }

            DocumentHistoryUserEvent.DeleteDialogDismissed -> {
              uiState = uiState.copy(documentToDelete = null)
            }

            is DocumentHistoryUserEvent.DocumentDeleteClicked -> {
              uiState = uiState.copy(documentToDelete = documents[0])
            }

            DocumentHistoryUserEvent.OnCreate -> {}
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
  fun alertDialogShouldBeShownWhenUserClickDeleteDocument() {
    setupScreen(
      uiState = DocumentHistoryUiState(
        documentToDelete = GeneratedDocument(
          id = 1,
          title = "Title",
          content = "Content",
          generatedDate = "22.11.2025"
        )
      )
    )

    rule.onNodeWithTag("delete_doc_alert").assertExists()
  }

  @Test
  fun hideDeleteAlertDialogWhenUserDismissIt() {
    setupScreen(DocumentHistoryUiState(documentToDelete = documents[0]))
    /*At this point dialog is shown*/
    rule.onNodeWithTag("delete_doc_alert").assertExists()

    /*Then user performs click to dismiss it*/
    rule.onNodeWithText("Откажи").assertExists().also { it.performClick() }

    /*Now alert dialog should be removed*/
    rule.onNodeWithTag("delete_doc_alert").assertDoesNotExist()
  }

  @Test
  fun hideDeleteAlertDialogWhenUserConfirmsIt() {
    setupScreen(DocumentHistoryUiState(documentToDelete = documents[0]))
    /*At this point dialog is shown*/
    rule.onNodeWithTag("delete_doc_alert").assertExists()

    /*Then user performs click to dismiss it*/
    rule.onNodeWithText("Избриши").assertExists().also { it.performClick() }

    /*Now alert dialog should be removed*/
    rule.onNodeWithTag("delete_doc_alert").assertDoesNotExist()
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
  fun whenDocumentsAreFetchedThenShowThem() {
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