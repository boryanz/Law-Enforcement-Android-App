package com.boryanz.upszakoni.ui.owneditem.addowneditem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.NumberPicker
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.components.input.TextFieldInput
import com.boryanz.upszakoni.ui.theme.UpsTheme

@Composable
fun OwnedItemContent(
  uiState: OwnedItemUiState,
  onBackClicked: () -> Unit,
  onItemNameChanged: (String) -> Unit,
  onPiecesCountChanged: (Int) -> Unit,
  onSaveClicked: () -> Unit,
) {
  UpsScaffold(
    topBarTitle = { Text("Задолжени предмети") },
    navigationIcon = {
      Icons.Back(onClick = onBackClicked)
    },
    trailingIcon = {
      Icons.Save(
        onClick = onSaveClicked,
        isEnabled = !uiState.hasItemNameError && uiState.itemName.isNotEmpty()
      )
    }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)
        .padding(it),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column(verticalArrangement = Arrangement.Top) {
        TextFieldInput.BaseOutline(
          labelText = "Задолжен предмет",
          value = uiState.itemName,
          isError = uiState.hasItemNameError,
          textStyle = MaterialTheme.typography.titleLarge,
          labelTextStyle = MaterialTheme.typography.bodyMedium,
          onValueChanged = onItemNameChanged,
          keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
          )
        )
        Spacer.Vertical(8.dp)
        NumberPicker.Simple(
          value = uiState.piecesCount,
          onValueChange = onPiecesCountChanged,
          minValue = 0,
          maxValue = 1000,
          label = "Парчиња"
        )
      }
    }
  }
}

class OwnedItemUiStatePreviewParameterProvider : PreviewParameterProvider<OwnedItemUiState> {
  override val values = sequenceOf(
    OwnedItemUiState(),
    OwnedItemUiState(
      itemName = "Мебел",
      piecesCount = 5,
      hasItemNameError = false
    ),
    OwnedItemUiState(
      itemName = "",
      piecesCount = 0,
      hasItemNameError = true
    ),
    OwnedItemUiState(
      itemName = "Електрични уреди",
      piecesCount = 150,
      hasItemNameError = false
    ),
  )
}

@Preview(name = "With Data - Filled")
@Composable
private fun OwnedItemContentWithDataPreview(
  @PreviewParameter(OwnedItemUiStatePreviewParameterProvider::class) uiState: OwnedItemUiState,
) {
  UpsTheme {
    OwnedItemContent(
      uiState,
      onBackClicked = {},
      onSaveClicked = {},
      onItemNameChanged = {},
      onPiecesCountChanged = {}
    )
  }
}
