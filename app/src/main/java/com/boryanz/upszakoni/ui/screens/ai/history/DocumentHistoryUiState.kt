package com.boryanz.upszakoni.ui.screens.ai.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class DocumentHistoryUiState(
  val isLoading: Boolean = false,
  val generatedDocuments: List<GeneratedDocument> = emptyList(),
  val documentToDelete: GeneratedDocument? = null
)

@Entity(tableName = "generated_documents")
data class GeneratedDocument(
  @PrimaryKey(autoGenerate = true) val id: Int,
  @ColumnInfo("title") val title: String,
  @ColumnInfo("content") val content: String,
  @ColumnInfo("generatedDate") val generatedDate: String,
)
