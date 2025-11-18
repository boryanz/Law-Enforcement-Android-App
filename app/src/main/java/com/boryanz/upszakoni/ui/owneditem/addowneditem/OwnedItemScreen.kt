package com.boryanz.upszakoni.ui.owneditem.addowneditem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.data.local.database.model.owneditem.ItemCategory
import com.boryanz.upszakoni.ui.owneditem.addowneditem.OwnedItemUiEvent.ItemNameChanged
import com.boryanz.upszakoni.ui.owneditem.addowneditem.OwnedItemUiEvent.PiecesCountChanged
import com.boryanz.upszakoni.utils.collectEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun OwnedItemScreen(
  itemId: Int,
  itemName: String,
  volume: Int,
  category: ItemCategory,
  onBackClicked: () -> Unit,
) {
  val viewModel = koinViewModel<OwnedItemViewModel>()
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
    viewModel.onUiEvent(
      OwnedItemUiEvent.OnCreate(
        itemId = itemId,
        itemName = itemName,
        volume = volume,
        category = category
      )
    )
  }

  viewModel.event.collectEvents {
    when (it) {
      OwnedItemEvent.ItemSaved -> onBackClicked()
    }
  }

  OwnedItemContent(
    uiState = uiState,
    onBackClicked = onBackClicked,
    onItemNameChanged = { viewModel.onUiEvent(ItemNameChanged(it)) },
    onPiecesCountChanged = { viewModel.onUiEvent(PiecesCountChanged(it)) },
    onSaveClicked = { viewModel.onUiEvent(OwnedItemUiEvent.SaveClicked) }
  )
}
