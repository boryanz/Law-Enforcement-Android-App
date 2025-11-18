package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.domain.owneditem.DeleteOwnedItemUseCase

class FakeDeleteOwnedItemUseCase : DeleteOwnedItemUseCase {
  val deletedItems = mutableListOf<String>()

  override fun invoke(itemName: String) {
    deletedItems.add(itemName)
  }
}
