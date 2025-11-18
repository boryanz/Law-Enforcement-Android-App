package com.boryanz.upszakoni.domain.owneditem

import com.boryanz.upszakoni.data.local.database.OwnedItemsDao
import com.boryanz.upszakoni.data.local.database.model.owneditem.OwnedItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface AddOwnedItemUseCase {
  suspend operator fun invoke(item: OwnedItem)
}

class AddOwnedItemUseCaseImpl(
  private val ownedItemsDao: OwnedItemsDao,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AddOwnedItemUseCase {
  override suspend operator fun invoke(item: OwnedItem) = withContext(dispatcher) {
    ownedItemsDao.insertOwnedItem(item)
  }
}