package com.boryanz.upszakoni.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.boryanz.upszakoni.ui.screens.ai.history.GeneratedDocument
import kotlinx.coroutines.flow.Flow

abstract class BaseDocumentsDao<T> {

  abstract suspend fun insert(item: T)
  abstract fun getAll(): Flow<List<GeneratedDocument>>
  abstract suspend fun delete(item: T)
}

@Dao
abstract class DocumentsHistoryDao : BaseDocumentsDao<GeneratedDocument>() {

  @Insert(onConflict = REPLACE)
  abstract override suspend fun insert(item: GeneratedDocument)

  @Query("SELECT * FROM generated_documents")
  abstract override fun getAll(): Flow<List<GeneratedDocument>>

  @Delete
  abstract override suspend fun delete(item: GeneratedDocument)
}