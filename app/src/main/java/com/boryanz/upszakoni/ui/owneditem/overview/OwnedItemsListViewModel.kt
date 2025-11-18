package com.boryanz.upszakoni.ui.owneditem.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.database.model.owneditem.OwnedItem
import com.boryanz.upszakoni.domain.owneditem.DeleteOwnedItemUseCase
import com.boryanz.upszakoni.domain.owneditem.GetOwnedItemsUseCase
import com.boryanz.upszakoni.ui.owneditem.overview.OwnedItemsListUiEvent.AlertDialogConfirmed
import com.boryanz.upszakoni.ui.owneditem.overview.OwnedItemsListUiEvent.AlertDialogDismissed
import com.boryanz.upszakoni.ui.owneditem.overview.OwnedItemsListUiEvent.DeleteItemClicked
import com.boryanz.upszakoni.ui.owneditem.overview.OwnedItemsListUiEvent.OnCreate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class OwnedItemData(
  val id: String,
  val name: String,
  val pieces: Int,
)

sealed interface OwnedItemsListUiEvent {
  data object OnCreate : OwnedItemsListUiEvent
  data class DeleteItemClicked(val item: OwnedItem) : OwnedItemsListUiEvent
  data object AlertDialogDismissed : OwnedItemsListUiEvent
  data object AlertDialogConfirmed : OwnedItemsListUiEvent
}

data class OwnedItemsListUiState(
  val items: List<OwnedItem> = emptyList(),
  val isLoading: Boolean = false,
  val itemToDelete: OwnedItem? = null,
)

class OwnedItemsListViewModel(
  private val getOwnedItemsUseCase: GetOwnedItemsUseCase,
  private val deleteOwnedItemUseCase: DeleteOwnedItemUseCase,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
  private val _uiState = MutableStateFlow(OwnedItemsListUiState())
  val uiState = _uiState.asStateFlow()

  fun onUiEvent(event: OwnedItemsListUiEvent) = viewModelScope.launch {
    when (event) {
      OnCreate -> {
        withContext(dispatcher) {
          getOwnedItemsUseCase().collect { value ->
            _uiState.update {
              it.copy(items = value)
            }
          }
        }
      }

      is DeleteItemClicked -> _uiState.update { it.copy(itemToDelete = event.item) }

      AlertDialogDismissed -> _uiState.update { it.copy(itemToDelete = null) }

      AlertDialogConfirmed -> {
        viewModelScope.launch {
          withContext(dispatcher) {
            deleteOwnedItemUseCase(uiState.value.itemToDelete?.name.orEmpty()).also {
              _uiState.update { it.copy(itemToDelete = null) }
            }
          }
        }
      }
    }
  }
}
