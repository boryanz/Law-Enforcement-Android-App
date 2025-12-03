package com.boryanz.upszakoni.ui.screens.ai.history

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.compose.LifecycleEventEffect
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data object DocumentHistoryDestination

sealed interface DocumentHistoryUserEvent {
  data object OnCreate : DocumentHistoryUserEvent
  data class DocumentDeleteClicked(val document: GeneratedDocument) : DocumentHistoryUserEvent
  data object DeleteDialogDismissed : DocumentHistoryUserEvent
  data object DeleteDialogConfirmed : DocumentHistoryUserEvent
}

@Composable
fun DocumentHistoryScreen(
  onBackClicked: () -> Unit,
  onAddDocumentClicked: () -> Unit,
  onDocumentClicked: (content: String) -> Unit,
) {

  val viewmodel = koinViewModel<DocumentHistoryViewModel>()

  LifecycleEventEffect(ON_CREATE) {
    viewmodel.onUserEvent(DocumentHistoryUserEvent.OnCreate)
  }

  DocumentHistoryContent(
    uiState = DocumentHistoryUiState(),
    onUserEvent = viewmodel::onUserEvent,
    onAddDocumentClicked = onAddDocumentClicked,
    onDocumentClicked = { onDocumentClicked(it) },
    onBackClicked = onBackClicked,
  )
}