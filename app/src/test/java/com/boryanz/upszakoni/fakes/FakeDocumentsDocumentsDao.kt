package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.data.local.database.BaseDocumentsDao
import com.boryanz.upszakoni.ui.screens.ai.history.GeneratedDocument
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDocumentsDocumentsDao(val documents: List<GeneratedDocument>) :
  BaseDocumentsDao<GeneratedDocument>() {

  override suspend fun insert(item: GeneratedDocument) {
    TODO("Not yet implemented")
  }

  override fun getAll(): Flow<List<GeneratedDocument>> {
    return flowOf(documents)
  }

  override suspend fun delete(item: GeneratedDocument) {
    TODO("Not yet implemented")
  }
}