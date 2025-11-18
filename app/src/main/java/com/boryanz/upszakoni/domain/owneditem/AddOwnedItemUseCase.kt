package com.boryanz.upszakoni.domain.owneditem

import com.boryanz.upszakoni.data.local.database.OwnedItemsDao
import com.boryanz.upszakoni.data.local.database.model.owneditem.OwnedItem

class AddOwnedItemUseCase(private val ownedItemsDao: OwnedItemsDao) {
  operator fun invoke(item: OwnedItem) = ownedItemsDao.insertOwnedItem(item)
}