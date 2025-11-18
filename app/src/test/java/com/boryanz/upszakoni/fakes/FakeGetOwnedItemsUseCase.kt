package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.data.local.database.model.owneditem.OwnedItem
import com.boryanz.upszakoni.domain.owneditem.GetOwnedItemsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGetOwnedItemsUseCase(
  private val items: List<OwnedItem> = emptyList()
) : GetOwnedItemsUseCase {
  override fun invoke(): Flow<List<OwnedItem>> = flowOf(items)
}
