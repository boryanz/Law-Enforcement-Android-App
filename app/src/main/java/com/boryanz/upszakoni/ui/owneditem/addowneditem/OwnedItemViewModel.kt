package com.boryanz.upszakoni.ui.owneditem.addowneditem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.database.model.owneditem.ItemCategory
import com.boryanz.upszakoni.data.local.database.model.owneditem.OwnedItem
import com.boryanz.upszakoni.domain.owneditem.AddOwnedItemUseCase
import com.boryanz.upszakoni.ui.owneditem.addowneditem.OwnedItemUiEvent.ItemNameChanged
import com.boryanz.upszakoni.ui.owneditem.addowneditem.OwnedItemUiEvent.OnCreate
import com.boryanz.upszakoni.ui.owneditem.addowneditem.OwnedItemUiEvent.PiecesCountChanged
import com.boryanz.upszakoni.ui.owneditem.addowneditem.OwnedItemUiEvent.SaveClicked
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

sealed interface OwnedItemUiEvent {
  data class OnCreate(
    val itemId: Int,
    val itemName: String,
    val volume: Int,
    val category: String
  ) : OwnedItemUiEvent

  data class ItemNameChanged(val value: String) : OwnedItemUiEvent
  data class PiecesCountChanged(val value: Int) : OwnedItemUiEvent
  data object SaveClicked : OwnedItemUiEvent
}

sealed interface OwnedItemEvent {
  data object ItemSaved : OwnedItemEvent
}

data class OwnedItemUiState(
  val itemId: Int = 0,
  val itemName: String = "",
  val piecesCount: Int = 0,
  val category: String = ItemCategory.OTHER.name,
  val hasItemNameError: Boolean = false,
)

class OwnedItemViewModel(private val addOwnedItemUseCase: AddOwnedItemUseCase) : ViewModel() {
  private val _uiState = MutableStateFlow(OwnedItemUiState())
  val uiState = _uiState.asStateFlow()

  private val _event = MutableSharedFlow<OwnedItemEvent>()
  val event = _event.asSharedFlow()

  fun onUiEvent(event: OwnedItemUiEvent) {
    when (event) {
      is ItemNameChanged -> {
        _uiState.value = _uiState.value.copy(
          itemName = event.value,
          hasItemNameError = event.value.isBlank()
        )
      }

      is PiecesCountChanged -> {
        _uiState.value = _uiState.value.copy(piecesCount = event.value)
      }

      SaveClicked -> {
        viewModelScope.launch {
          addOwnedItemUseCase(
            OwnedItem(
              id = uiState.value.itemId,
              name = uiState.value.itemName,
              volume = uiState.value.piecesCount,
              category = uiState.value.category,
              date = getCurrentDateFormatted()
            )
          )
          _event.emit(OwnedItemEvent.ItemSaved)
        }
      }

      is OnCreate -> _uiState.update {
        it.copy(
          itemId = event.itemId,
          itemName = event.itemName,
          piecesCount = event.volume,
          category = event.category,
          hasItemNameError = false
        )
      }
    }
  }

  fun getCurrentDateFormatted(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return currentDate.format(formatter)
  }
}
