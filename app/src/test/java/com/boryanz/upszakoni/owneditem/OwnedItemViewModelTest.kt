package com.boryanz.upszakoni.owneditem

import com.boryanz.upszakoni.MainDispatcherRule
import com.boryanz.upszakoni.data.local.database.model.owneditem.ItemCategory
import com.boryanz.upszakoni.fakes.FakeAddOwnedItemUseCase
import com.boryanz.upszakoni.ui.owneditem.addowneditem.OwnedItemEvent
import com.boryanz.upszakoni.ui.owneditem.addowneditem.OwnedItemUiEvent
import com.boryanz.upszakoni.ui.owneditem.addowneditem.OwnedItemUiState
import com.boryanz.upszakoni.ui.owneditem.addowneditem.OwnedItemViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class OwnedItemViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Test
  fun `update initial ui state with data from the ui event`() = runTest {
    //Given
    val expectedState = OwnedItemUiState(
      itemId = 1,
      itemName = "Glock 17",
      piecesCount = 1,
      category = ItemCategory.FIREARM.name,
      hasItemNameError = false
    )
    val viewmodel = OwnedItemViewModel(addOwnedItemUseCase = FakeAddOwnedItemUseCase())
    //When
    viewmodel.onUiEvent(
      OwnedItemUiEvent.OnCreate(
        itemId = expectedState.itemId,
        itemName = expectedState.itemName,
        volume = expectedState.piecesCount,
        category = expectedState.category
      )
    )
    //Then
    assertEquals(
      expectedState,
      viewmodel.uiState.value
    )
  }

  @Test
  fun `when item name change update ui state without error`() = runTest {
    //Given
    val expectedState = OwnedItemUiState(
      itemId = 1,
      itemName = "",
      piecesCount = 1,
      category = ItemCategory.FIREARM.name,
      hasItemNameError = false
    )
    val viewmodel = OwnedItemViewModel(addOwnedItemUseCase = FakeAddOwnedItemUseCase())
    //When
    viewmodel.onUiEvent(
      OwnedItemUiEvent.OnCreate(
        itemId = expectedState.itemId,
        itemName = expectedState.itemName,
        volume = expectedState.piecesCount,
        category = expectedState.category
      )
    )
    viewmodel.onUiEvent(
      OwnedItemUiEvent.ItemNameChanged(value = "Glo")
    )
    //Then
    assertEquals(
      expectedState.copy(itemName = "Glo"),
      viewmodel.uiState.value
    )

    viewmodel.onUiEvent(
      OwnedItemUiEvent.ItemNameChanged(value = "Glock 17")
    )

    assertEquals(
      expectedState.copy(itemName = "Glock 17"),
      viewmodel.uiState.value
    )
  }

  @Test
  fun `when item name change update ui state WITH error in name`() = runTest {
    //Given
    val expectedState = OwnedItemUiState(
      itemId = 1,
      itemName = "",
      piecesCount = 1,
      category = ItemCategory.FIREARM.name,
      hasItemNameError = false
    )
    val viewmodel = OwnedItemViewModel(addOwnedItemUseCase = FakeAddOwnedItemUseCase())
    //When
    viewmodel.onUiEvent(
      OwnedItemUiEvent.OnCreate(
        itemId = expectedState.itemId,
        itemName = expectedState.itemName,
        volume = expectedState.piecesCount,
        category = expectedState.category
      )
    )
    viewmodel.onUiEvent(
      OwnedItemUiEvent.ItemNameChanged(value = "")
    )
    //Then
    assertEquals(
      expectedState.copy(itemName = "", hasItemNameError = true),
      viewmodel.uiState.value
    )
  }

  @Test
  fun `when pieces count changes update ui state with the value`() = runTest {
    //Given
    val expectedState = OwnedItemUiState(
      itemId = 1,
      itemName = "",
      piecesCount = 0,
      category = ItemCategory.FIREARM.name,
      hasItemNameError = false
    )
    val viewmodel = OwnedItemViewModel(addOwnedItemUseCase = FakeAddOwnedItemUseCase())
    //When
    viewmodel.onUiEvent(
      OwnedItemUiEvent.OnCreate(
        itemId = expectedState.itemId,
        itemName = expectedState.itemName,
        volume = expectedState.piecesCount,
        category = expectedState.category
      )
    )
    //Then
    assertEquals(
      expectedState.copy(piecesCount = 0),
      viewmodel.uiState.value
    )

    //When
    viewmodel.onUiEvent(
      OwnedItemUiEvent.PiecesCountChanged(value = 2)
    )
    //Then
    assertEquals(
      expectedState.copy(piecesCount = 2),
      viewmodel.uiState.value
    )
  }

  @Ignore("Event is not collected for some reason, check it later")
  @Test
  fun `when save is clicked then save the data and emit Item Saved event`() = runTest {
    //Given
    val fakeUseCase = FakeAddOwnedItemUseCase()
    val expectedState = OwnedItemUiState(
      itemId = 1,
      itemName = "Glock 17",
      piecesCount = 5,
      category = ItemCategory.FIREARM.name,
      hasItemNameError = false
    )
    val viewmodel = OwnedItemViewModel(addOwnedItemUseCase = fakeUseCase)
    //When
    viewmodel.onUiEvent(
      OwnedItemUiEvent.OnCreate(
        itemId = expectedState.itemId,
        itemName = expectedState.itemName,
        volume = expectedState.piecesCount,
        category = expectedState.category
      )
    )

    //When
    viewmodel.onUiEvent(OwnedItemUiEvent.SaveClicked)

    //Then
    val event = viewmodel.event.first()
    assertTrue(event is OwnedItemEvent.ItemSaved)
    assertEquals(1, fakeUseCase.addedItems.size)
    assertEquals(expectedState.itemName, fakeUseCase.addedItems[0].name)
  }
}