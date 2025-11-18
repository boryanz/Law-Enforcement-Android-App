package com.boryanz.upszakoni.domain.owneditem

import com.boryanz.upszakoni.data.local.database.OwnedItemsDao
import com.boryanz.upszakoni.data.local.database.model.owneditem.OwnedItem
import kotlinx.coroutines.flow.Flow

class GetOwnedItemsUseCase(
  private val ownedItemDao: OwnedItemsDao
) {
  operator fun invoke(): Flow<List<OwnedItem>> = ownedItemDao.getAllOwnedItems()
}