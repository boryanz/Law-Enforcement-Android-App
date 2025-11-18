package com.boryanz.upszakoni.ui.owneditem.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.data.local.database.model.owneditem.OwnedItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun OwnedItemsListScreen(
  onBackClicked: () -> Unit,
  onItemClick: (OwnedItem) -> Unit = {},
  onAddItemClicked: () -> Unit = {},
) {
  val viewModel = koinViewModel<OwnedItemsListViewModel>()
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
    viewModel.onUiEvent(OwnedItemsListUiEvent.OnCreate)
  }

  OwnedItemsListContent(
    uiState = uiState,
    onBackClicked = onBackClicked,
    onItemClick = onItemClick,
    onAddItemClicked = onAddItemClicked,
    onUiEvent = { viewModel.onUiEvent(it) }
  )
}
