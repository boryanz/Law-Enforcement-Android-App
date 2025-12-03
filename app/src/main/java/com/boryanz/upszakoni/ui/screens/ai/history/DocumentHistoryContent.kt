package com.boryanz.upszakoni.ui.screens.ai.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.Loader
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.theme.Base100
import com.boryanz.upszakoni.ui.theme.BaseContent
import com.boryanz.upszakoni.ui.theme.UpsTheme

@Composable
fun DocumentHistoryContent(
  uiState: DocumentHistoryUiState,
  onUserEvent: (DocumentHistoryUserEvent) -> Unit,
  onAddDocumentClicked: () -> Unit,
  onDocumentClicked: (content: String) -> Unit,
  onBackClicked: () -> Unit,
) {
  UpsScaffold(
    topBarTitle = { Text(stringResource(R.string.document_history_screen_title)) },
    floatingActionButton = {
      if (!uiState.isLoading) {
        FabButton(onClick = onAddDocumentClicked)
      }
    },
    navigationIcon = {
      com.boryanz.upszakoni.ui.components.Icons.Back(onClick = onBackClicked)
    }
  ) { paddingValues ->
    if (uiState.documentToDelete != null) {
      AlertDialog(
        modifier = Modifier.testTag("delete_doc_alert"),
        onDismissRequest = { onUserEvent(DocumentHistoryUserEvent.DeleteDialogDismissed) },
        text = { Text("Дали сте сигурни дека сакате да ја избришете белешката?") },
        confirmButton = {
          TextButton(
            onClick = { onUserEvent(DocumentHistoryUserEvent.DeleteDialogConfirmed) }
          ) {
            Text("Избриши")
          }
        },
        dismissButton = {
          TextButton(onClick = { onUserEvent(DocumentHistoryUserEvent.DeleteDialogDismissed) }) {
            Text("Откажи")
          }
        }
      )
    }

    when {
      uiState.isLoading -> Loader()
      else -> {
        if (uiState.generatedDocuments.isEmpty()) {
          EmptyState(paddingValues)
        } else {
          CompleteData(
            uiState = uiState,
            paddingValues = paddingValues,
            onDocumentClicked = { onDocumentClicked(it) },
            onUserEvent = { onUserEvent(it) }
          )
        }
      }
    }
  }
}

@Composable
fun CompleteData(
  uiState: DocumentHistoryUiState,
  paddingValues: PaddingValues,
  onDocumentClicked: (content: String) -> Unit,
  onUserEvent: (DocumentHistoryUserEvent) -> Unit,
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(paddingValues)
      .padding(8.dp),
    verticalArrangement = Arrangement.spacedBy(4.dp)
  ) {
    items(items = uiState.generatedDocuments, key = { it.id }) { document ->
      GeneratedDocumentItem(
        document = document,
        onDocumentClicked = { onDocumentClicked(it) },
        onDeleteDocumentClicked = {
          onUserEvent(DocumentHistoryUserEvent.DocumentDeleteClicked(document))
        }
      )
    }
  }
}

@Composable
fun FabButton(onClick: () -> Unit) {
  FloatingActionButton(
    contentColor = BaseContent,
    backgroundColor = Base100,
    onClick = onClick
  ) {
    Icon(imageVector = Icons.Default.Add, contentDescription = "generate_document_fab")
  }
}

@Composable
fun EmptyState(paddingValues: PaddingValues) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(paddingValues),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      modifier = Modifier.fillMaxWidth(),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyLarge,
      text = stringResource(R.string.no_ai_generated_documents_info_text)
    )
  }
}

val documentHistoryUiState = DocumentHistoryUiState(
  isLoading = false,
  generatedDocuments = emptyList(),
)

val generatedDocs = listOf(
  GeneratedDocument(
    id = 1,
    title = "ЈРМ",
    content = "На ден 13.05.2025 во 14:30ч на Ул. Франце Прешерн бб е пријавено нарушување на ЈРМ.",
    generatedDate = "13.05.2025"
  ),
  GeneratedDocument(
    id = 2,
    title = "ЈРМ",
    content = "На ден 13.05.2025 во 14:30ч на Ул. Франце Прешерн бб е пријавено нарушување на ЈРМ.",
    generatedDate = "13.05.2025"
  ),
  GeneratedDocument(
    id = 3,
    title = "ЈРМ",
    content = "На ден 13.05.2025 во 14:30ч на Ул. Франце Прешерн бб е пријавено нарушување на ЈРМ.",
    generatedDate = "13.05.2025"
  )
)

class DocumentHistoryUiStateProvider : PreviewParameterProvider<DocumentHistoryUiState> {
  override val values: Sequence<DocumentHistoryUiState>
    get() = sequenceOf(
      documentHistoryUiState,
      documentHistoryUiState.copy(isLoading = true),
      documentHistoryUiState.copy(documentToDelete = generatedDocs[0]),
      documentHistoryUiState.copy(generatedDocuments = generatedDocs)
    )
}

@PreviewLightDark
@Composable
private fun DocumentHistoryContentPreview(
  @PreviewParameter(DocumentHistoryUiStateProvider::class) uiState: DocumentHistoryUiState
) {
  UpsTheme {
    DocumentHistoryContent(
      uiState = uiState,
      onDocumentClicked = {},
      onAddDocumentClicked = {},
      onBackClicked = {},
      onUserEvent = {}
    )
  }
}



