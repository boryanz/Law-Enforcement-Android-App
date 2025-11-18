package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.data.local.database.model.owneditem.OwnedItem
import com.boryanz.upszakoni.domain.owneditem.AddOwnedItemUseCase

class FakeAddOwnedItemUseCase : AddOwnedItemUseCase {
  val addedItems = mutableListOf<OwnedItem>()

  override fun invoke(item: OwnedItem) {
    addedItems.add(item)
  }
}
