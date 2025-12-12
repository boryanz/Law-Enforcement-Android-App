package com.boryanz.upszakoni.ui.screens.ai.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
  onMoreInformationClicked: () -> Unit,
) {
  val viewmodel = koinViewModel<DocumentHistoryViewModel>()
  val uiState by viewmodel.uiState.collectAsStateWithLifecycle()

  LifecycleEventEffect(ON_CREATE) {
    viewmodel.onUserEvent(DocumentHistoryUserEvent.OnCreate)
  }

  DocumentHistoryContent(
    uiState = uiState,
    onUserEvent = viewmodel::onUserEvent,
    onAddDocumentClicked = onAddDocumentClicked,
    onDocumentClicked = { onDocumentClicked(it) },
    onMoreInformationClicked = onMoreInformationClicked,
    onBackClicked = onBackClicked,
  )
}