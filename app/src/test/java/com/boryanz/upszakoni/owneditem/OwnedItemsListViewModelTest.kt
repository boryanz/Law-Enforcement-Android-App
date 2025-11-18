package com.boryanz.upszakoni.owneditem

import com.boryanz.upszakoni.MainDispatcherRule
import com.boryanz.upszakoni.data.local.database.model.owneditem.ItemCategory
import com.boryanz.upszakoni.data.local.database.model.owneditem.OwnedItem
import com.boryanz.upszakoni.fakes.FakeDeleteOwnedItemUseCase
import com.boryanz.upszakoni.fakes.FakeGetOwnedItemsUseCase
import com.boryanz.upszakoni.ui.owneditem.overview.OwnedItemsListUiEvent
import com.boryanz.upszakoni.ui.owneditem.overview.OwnedItemsListUiState
import com.boryanz.upszakoni.ui.owneditem.overview.OwnedItemsListViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class OwnedItemsListViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Test
  fun `get owned items successfully and update ui state`() = runTest {
    //Given
    val expected = listOf(
      OwnedItem(
        id = 1,
        name = "Glok 17",
        volume = 1,
        category = ItemCategory.AMMUNITION
      ),
      OwnedItem(
        id = 1,
        name = "Glok 18",
        volume = 1,
        category = ItemCategory.AMMUNITION
      )
    )

    val viewModel = OwnedItemsListViewModel(
      getOwnedItemsUseCase = FakeGetOwnedItemsUseCase(expected),
      deleteOwnedItemUseCase = FakeDeleteOwnedItemUseCase(),
    )
    //When
    viewModel.onUiEvent(OwnedItemsListUiEvent.OnCreate)

    //Then
    Assert.assertEquals(
      OwnedItemsListUiState(
        items = expected,
        isLoading = false,
        itemToDelete = null,
      ),
      viewModel.uiState.value
    )
  }

  @Test
  fun `when delete item is clicked update ui state with the item`() = runTest {
    //Given
    val expected = listOf(
      OwnedItem(
        id = 1,
        name = "Glok 17",
        volume = 1,
        category = ItemCategory.AMMUNITION
      ),
      OwnedItem(
        id = 2,
        name = "Glok 18",
        volume = 1,
        category = ItemCategory.AMMUNITION
      )
    )

    val viewModel = OwnedItemsListViewModel(
      getOwnedItemsUseCase = FakeGetOwnedItemsUseCase(expected),
      deleteOwnedItemUseCase = FakeDeleteOwnedItemUseCase(),
    )
    //When
    viewModel.onUiEvent(OwnedItemsListUiEvent.DeleteItemClicked(expected.first()))

    //Then
    Assert.assertEquals(
      expected.first(),
      viewModel.uiState.value.itemToDelete
    )
  }

  @Test
  fun `set item to delete to null when alert dialog is dismissed`() = runTest {
    //Given
    val expected = listOf(
      OwnedItem(
        id = 1,
        name = "Glok 17",
        volume = 1,
        category = ItemCategory.AMMUNITION
      ),
      OwnedItem(
        id = 1,
        name = "Glok 18",
        volume = 1,
        category = ItemCategory.AMMUNITION
      )
    )

    val viewModel = OwnedItemsListViewModel(
      getOwnedItemsUseCase = FakeGetOwnedItemsUseCase(expected),
      deleteOwnedItemUseCase = FakeDeleteOwnedItemUseCase(),
    )
    //When
    viewModel.onUiEvent(OwnedItemsListUiEvent.DeleteItemClicked(expected.first()))

    //Then
    Assert.assertEquals(
      expected.first(),
      viewModel.uiState.value.itemToDelete
    )

    viewModel.onUiEvent(OwnedItemsListUiEvent.AlertDialogDismissed)

    Assert.assertEquals(
      null,
      viewModel.uiState.value.itemToDelete
    )
  }


  @Test
  fun `when alert dialog is confirmed then delete item successfully and update item to delete to null`() =
    runTest {
      //Given
      val expected = listOf(
        OwnedItem(
          id = 1,
          name = "Glok 17",
          volume = 1,
          category = ItemCategory.AMMUNITION
        ),
        OwnedItem(
          id = 2,
          name = "Glok 18",
          volume = 1,
          category = ItemCategory.AMMUNITION
        )
      )

      val viewModel = OwnedItemsListViewModel(
        getOwnedItemsUseCase = FakeGetOwnedItemsUseCase(expected),
        deleteOwnedItemUseCase = FakeDeleteOwnedItemUseCase(),
      )
      //When
      viewModel.onUiEvent(OwnedItemsListUiEvent.OnCreate)

      //Then
      Assert.assertEquals(
        OwnedItemsListUiState(
          items = expected,
          isLoading = false,
          itemToDelete = null,
        ),
        viewModel.uiState.value
      )

      //When
      viewModel.onUiEvent(OwnedItemsListUiEvent.DeleteItemClicked(expected.first()))

      //Then
      Assert.assertEquals(
        expected.first(),
        viewModel.uiState.value.itemToDelete
      )

      //When
      viewModel.onUiEvent(OwnedItemsListUiEvent.AlertDialogConfirmed)

      //Then
      Assert.assertEquals(
        null,
        viewModel.uiState.value.itemToDelete
      )
    }
}