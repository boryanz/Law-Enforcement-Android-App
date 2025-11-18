package com.boryanz.upszakoni.domain.owneditem

import com.boryanz.upszakoni.data.local.database.OwnedItemsDao

interface DeleteOwnedItemUseCase {
  operator fun invoke(itemName: String)
}

class DeleteOwnedItemUseCaseImpl(private val ownedItemDao: OwnedItemsDao) : DeleteOwnedItemUseCase {
  override operator fun invoke(itemName: String) = ownedItemDao.deleteOwnedItem(itemName)
}