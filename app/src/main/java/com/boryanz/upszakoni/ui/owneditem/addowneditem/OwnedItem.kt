package com.boryanz.upszakoni.ui.owneditem.addowneditem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.data.local.database.model.owneditem.ItemCategory
import com.boryanz.upszakoni.data.local.database.model.owneditem.OwnedItem
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.RowItem
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.theme.UpsTheme

@Composable
fun OwnedItem(
  item: OwnedItem,
  onDeleteClick: (String) -> Unit,
  onClick: (String) -> Unit = {},
) {
  RowItem(
    onClick = { onClick(item.name) },
    isEnabled = true,
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = item.name,
          style = MaterialTheme.typography.titleMedium,
        )
        Spacer.Vertical(2.dp)
        Text(
          text = "Внесено на ${item.date}",
          style = MaterialTheme.typography.bodyMedium,
        )
      }

      Text(
        text = "${item.volume}x",
        style = MaterialTheme.typography.titleMedium,
      )
      Icons.Base(
        modifier = Modifier.size(20.dp),
        imageVector = androidx.compose.material.icons.Icons.Filled.Delete,
        onClick = { onDeleteClick(item.name) }
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun OwnedItemPreview() {
  UpsTheme {
    OwnedItem(
      item = OwnedItem(
        name = "Мебел",
        volume = 5,
        category = ItemCategory.AMMUNITION.name,
        date = "21.11.2025"
      ),
      onDeleteClick = {},
      onClick = {}
    )
  }
}
