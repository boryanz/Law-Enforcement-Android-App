package com.boryanz.upszakoni.ui.owneditem.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.data.local.database.model.owneditem.ItemCategory
import com.boryanz.upszakoni.data.local.database.model.owneditem.OwnedItem
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.owneditem.addowneditem.OwnedItem
import com.boryanz.upszakoni.ui.theme.Base100
import com.boryanz.upszakoni.ui.theme.BaseContent
import com.boryanz.upszakoni.ui.theme.UpsTheme

@Composable
fun OwnedItemsListContent(
  uiState: OwnedItemsListUiState,
  onBackClicked: () -> Unit,
  onItemClick: (OwnedItem) -> Unit,
  onAddItemClicked: () -> Unit,
  onUiEvent: (OwnedItemsListUiEvent) -> Unit,
) {
  if (uiState.itemToDelete != null) {
    AlertDialog(
      onDismissRequest = { onUiEvent(OwnedItemsListUiEvent.AlertDialogDismissed) },
      text = { Text("Дали сте сигурни дека сакате да го избришете ${uiState.itemToDelete.name}?") },
      confirmButton = {
        TextButton(
          onClick = { onUiEvent(OwnedItemsListUiEvent.AlertDialogConfirmed) }
        ) {
          Text("Избриши")
        }
      },
      dismissButton = {
        TextButton(onClick = { onUiEvent(OwnedItemsListUiEvent.AlertDialogDismissed) }) {
          Text("Откажи")
        }
      }
    )
  }

  UpsScaffold(
    floatingActionButton = {
      FloatingActionButton(
        containerColor = Base100,
        contentColor = BaseContent,
        onClick = onAddItemClicked,
      ) {
        Icon(
          imageVector = androidx.compose.material.icons.Icons.Filled.Add,
          contentDescription = "Add new item"
        )
      }
    },
    topBarTitle = { Text(stringResource(R.string.owned_items_list_screen_title)) },
    navigationIcon = {
      Icons.Back(onClick = onBackClicked)
    }
  ) { paddingValues ->
    if (uiState.isLoading) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        CircularProgressIndicator()
      }
    } else if (uiState.items.isEmpty()) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues)
          .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(stringResource(R.string.no_owned_items_info_text))
      }
    } else {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues)
          .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        items(
          items = uiState.items,
          key = { it.name }
        ) { item ->
          OwnedItem(
            item = item,
            onDeleteClick = { onUiEvent(OwnedItemsListUiEvent.DeleteItemClicked(item)) },
            onClick = { onItemClick(item) }
          )
        }
      }
    }
  }
}

class OwnedItemsListUiStatePreviewParameterProvider :
  PreviewParameterProvider<OwnedItemsListUiState> {
  override val values = sequenceOf(
    OwnedItemsListUiState(
      items = emptyList(),
      isLoading = false
    ),
    OwnedItemsListUiState(
      items = emptyList(),
      isLoading = true
    ),
    OwnedItemsListUiState(
      items = listOf(
        OwnedItem(
          name = "Мебел",
          volume = 5,
          category = ItemCategory.AMMUNITION.name,
          date = "21.11.2025"
        ),
      ),
      isLoading = false
    ),
    OwnedItemsListUiState(
      items = listOf(
        OwnedItem(
          name = "Мебел",
          volume = 5,
          category = ItemCategory.AMMUNITION.name,
          date = "21.11.2025"
        ),
        OwnedItem(
          name = "Електрични уреди",
          volume = 12,
          category = ItemCategory.AMMUNITION.name,
          date = "21.11.2025"
        ),
        OwnedItem(
          name = "Инвентар",
          volume = 25,
          category = ItemCategory.AMMUNITION.name,
          date = "21.11.2025"
        ),
        OwnedItem(
          name = "Софтвер",
          volume = 3,
          category = ItemCategory.AMMUNITION.name,
          date = "21.11.2025"
        ),
        OwnedItem(
          name = "Документи",
          volume = 150,
          category = ItemCategory.AMMUNITION.name,
          date = "21.11.2025"
        ),
      ),
      isLoading = false
    ),
  )
}

@Preview(name = "Empty State")
@Composable
private fun OwnedItemsListContentEmptyPreview() {
  UpsTheme {
    OwnedItemsListContent(
      uiState = OwnedItemsListUiState(
        items = emptyList(),
        isLoading = false
      ),
      onBackClicked = {},
      onItemClick = {},
      onAddItemClicked = {},
      onUiEvent = {}
    )
  }
}

@Preview(name = "Loading State")
@Composable
private fun OwnedItemsListContentLoadingPreview() {
  UpsTheme {
    OwnedItemsListContent(
      uiState = OwnedItemsListUiState(
        items = emptyList(),
        isLoading = true
      ),
      onBackClicked = {},
      onItemClick = {},
      onAddItemClicked = {},
      onUiEvent = {}
    )
  }
}

@Preview(name = "With Data")
@Composable
private fun OwnedItemsListContentWithDataPreview(
  @PreviewParameter(OwnedItemsListUiStatePreviewParameterProvider::class) uiState: OwnedItemsListUiState,
) {
  UpsTheme {
    OwnedItemsListContent(
      uiState = uiState,
      onBackClicked = {},
      onItemClick = {},
      onAddItemClicked = {},
      onUiEvent = {}
    )
  }
}

@Preview(name = "With Delete Dialog")
@Composable
private fun OwnedItemsListContentWithDeleteDialogPreview() {
  UpsTheme {
    OwnedItemsListContent(
      uiState = OwnedItemsListUiState(
        items = listOf(
          OwnedItem(
            name = "Мебел",
            volume = 5,
            category = ItemCategory.AMMUNITION.name,
            date = "21.11.2025"
          ),
          OwnedItem(
            name = "Електрични уреди",
            volume = 12,
            category = ItemCategory.AMMUNITION.name,
            date = "21.11.2025"
          ),
          OwnedItem(
            name = "Инвентар",
            volume = 25,
            category = ItemCategory.AMMUNITION.name,
            date = "21.11.2025"
          ),
        ),
        isLoading = false,
        itemToDelete = OwnedItem(
          name = "Инвентар",
          volume = 25,
          category = ItemCategory.AMMUNITION.name, date = "21.11.2025"
        ),
      ),
      onBackClicked = {},
      onItemClick = {},
      onAddItemClicked = {},
      onUiEvent = {}
    )
  }
}
