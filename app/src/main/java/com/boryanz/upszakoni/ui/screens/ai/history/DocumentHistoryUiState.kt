package com.boryanz.upszakoni.ui.screens.ai.history

data class DocumentHistoryUiState(
  val isLoading: Boolean = false,
  val generatedDocuments: List<GeneratedDocument> = emptyList(),
  val showDeleteDialog: Boolean = false,
)

data class GeneratedDocument(
  val id: Int,
  val title: String,
  val content: String,
  val generatedDate: String,
)
