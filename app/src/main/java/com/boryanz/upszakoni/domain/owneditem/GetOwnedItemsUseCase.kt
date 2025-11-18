package com.boryanz.upszakoni.domain.owneditem

import com.boryanz.upszakoni.data.local.database.OwnedItemsDao
import com.boryanz.upszakoni.data.local.database.model.owneditem.OwnedItem
import kotlinx.coroutines.flow.Flow

interface GetOwnedItemsUseCase {
  operator fun invoke(): Flow<List<OwnedItem>>
}

class GetOwnedItemsUseCaseImpl(
  private val ownedItemDao: OwnedItemsDao
) : GetOwnedItemsUseCase {
  override operator fun invoke(): Flow<List<OwnedItem>> = ownedItemDao.getAllOwnedItems()
}