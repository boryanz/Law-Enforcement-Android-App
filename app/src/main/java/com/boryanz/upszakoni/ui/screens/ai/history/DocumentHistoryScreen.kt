package com.boryanz.upszakoni.ui.screens.ai.history

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data object DocumentHistoryDestination

sealed interface DocumentHistoryUserEvent {
  data object OnCreate : DocumentHistoryUserEvent
  data object FABClicked : DocumentHistoryUserEvent
  data class DocumentDeleteClicked(val documentId: Int) : DocumentHistoryUserEvent
  data object DeleteDialogDismissed : DocumentHistoryUserEvent
  data object DeleteDialogConfirmed : DocumentHistoryUserEvent
}

@Composable
fun DocumentHistoryScreen(onBackClicked: () -> Unit) {

  val viewmodel = koinViewModel<DocumentHistoryViewModel>()

  LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
    viewmodel.onUserEvent(DocumentHistoryUserEvent.OnCreate)
  }

  DocumentHistoryContent(
    uiState = DocumentHistoryUiState(),
    onUserEvent = { },
    onDocumentClicked = {},
    onBackClicked = {},
  )
}