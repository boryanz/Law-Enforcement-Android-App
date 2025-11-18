package com.boryanz.upszakoni.domain.owneditem

import com.boryanz.upszakoni.data.local.database.OwnedItemsDao

class DeleteOwnedItemUseCase(private val ownedItemDao: OwnedItemsDao) {

  operator fun invoke(itemName: String) = ownedItemDao.deleteOwnedItem(itemName)
}