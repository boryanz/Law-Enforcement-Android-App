package com.boryanz.upszakoni.ui.screens.ai.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.RowItem
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.theme.UpsTheme

@Composable
fun GeneratedDocumentItem(
  document: GeneratedDocument,
  onDocumentClicked: (content: String) -> Unit,
  onDeleteDocumentClicked: (id: Int) -> Unit,
) {
  RowItem(
    onClick = { onDocumentClicked(document.content) },
    isEnabled = true,
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Row(modifier = Modifier.fillMaxWidth()) {
          androidx.compose.material3.Text(
            text = document.title,
            style = MaterialTheme.typography.titleMedium,
          )
          Spacer.Horizontal(4.dp)
          androidx.compose.material3.Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = document.generatedDate,
            style = MaterialTheme.typography.bodyMedium,
          )
        }
        Spacer.Vertical(4.dp)
        androidx.compose.material3.Text(
          text = AnnotatedString.fromHtml(
            document.content.replace(
              oldValue = "СЛУЖБЕНА БЕЛЕШКА",
              newValue = "",
              ignoreCase = false
            )
          ),
          style = MaterialTheme.typography.bodyLarge,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis
        )
      }

      com.boryanz.upszakoni.ui.components.Icons.Base(
        modifier = Modifier.size(20.dp),
        contentDescription = "delete_ai_document_button",
        imageVector = Icons.Filled.Delete,
        onClick = { onDeleteDocumentClicked(document.id) }
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun DocumentItemPreview() {
  UpsTheme {
    GeneratedDocumentItem(
      onDeleteDocumentClicked = {},
      onDocumentClicked = {},
      document = GeneratedDocument(
        id = 1,
        title = "ЈРМ",
        content = "Нарушување ЈРМ со тепачка во толпа...",
        generatedDate = "30.11.2025"
      )
    )
  }
}