package com.boryanz.upszakoni.domain.owneditem

import com.boryanz.upszakoni.data.local.database.OwnedItemsDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface DeleteOwnedItemUseCase {
  suspend operator fun invoke(itemName: String)
}

class DeleteOwnedItemUseCaseImpl(
  private val ownedItemDao: OwnedItemsDao,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DeleteOwnedItemUseCase {
  override suspend operator fun invoke(itemName: String) = withContext(dispatcher) {
    ownedItemDao.deleteOwnedItem(itemName)
  }
}