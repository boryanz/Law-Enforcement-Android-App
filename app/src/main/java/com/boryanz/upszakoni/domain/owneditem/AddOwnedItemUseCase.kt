package com.boryanz.upszakoni.domain.owneditem

import com.boryanz.upszakoni.data.local.database.OwnedItemsDao
import com.boryanz.upszakoni.data.local.database.model.owneditem.OwnedItem

interface AddOwnedItemUseCase {
  operator fun invoke(item: OwnedItem)
}

class AddOwnedItemUseCaseImpl(private val ownedItemsDao: OwnedItemsDao) : AddOwnedItemUseCase {
  override operator fun invoke(item: OwnedItem) = ownedItemsDao.insertOwnedItem(item)
}