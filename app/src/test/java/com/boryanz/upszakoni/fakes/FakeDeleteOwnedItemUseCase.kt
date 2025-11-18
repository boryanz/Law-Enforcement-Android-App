package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.domain.owneditem.DeleteOwnedItemUseCase

class FakeDeleteOwnedItemUseCase : DeleteOwnedItemUseCase {
  val deletedItems = mutableListOf<String>()

  override suspend fun invoke(itemName: String) {
    deletedItems.add(itemName)
  }
}
