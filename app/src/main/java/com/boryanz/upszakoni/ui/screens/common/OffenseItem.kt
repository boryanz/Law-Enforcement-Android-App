package com.boryanz.upszakoni.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.RowItem
import com.boryanz.upszakoni.ui.components.Spacer

@Composable
fun OffenseItem(
  isEnabled: Boolean = false,
  description: String,
  article: String,
) {
  RowItem(isEnabled = isEnabled, onClick = {}) {
    Column(
      horizontalAlignment = Alignment.Start,
      verticalArrangement = Arrangement.Center
    ) {
      Text(text = article, fontWeight = FontWeight.Bold)
      Spacer.Horizontal(4.dp)
      Text(text = description)
    }
  }
}