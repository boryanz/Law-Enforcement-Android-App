package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.theme.Base100
import com.boryanz.upszakoni.ui.theme.BaseContent1
import com.boryanz.upszakoni.ui.theme.UpsTheme

@Composable
fun SegmentedControl(
  modifier: Modifier = Modifier,
  options: List<String> = listOf(),
  onSelectionChanged: (String) -> Unit
) {
  var selectedIndex by remember { mutableIntStateOf(0) }

  SingleChoiceSegmentedButtonRow(
    modifier = modifier.fillMaxWidth()
  ) {
    options.forEachIndexed { index, option ->
      SegmentedButton(
        modifier = Modifier.weight(1f),
        selected = selectedIndex == index,
        onClick = {
          onSelectionChanged(option)
          selectedIndex = index
        },
        label = {
          Text(
            text = option,
            style = MaterialTheme.typography.labelSmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
          )
        },
        shape = SegmentedButtonDefaults.itemShape(
          index = index,
          count = options.size
        ),
        colors = SegmentedButtonDefaults.colors(
          activeContainerColor = BaseContent1,
          activeContentColor = Base100,
          inactiveContainerColor = Color.Transparent,
        ),
        icon = { /* Not needed */ },
      )
    }
  }
}

@Preview(showBackground = true)
@PreviewLightDark
@Composable
private fun SegmentedControlPreview() {
  UpsTheme {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      SegmentedControl(
        options = listOf("Option 1", "Option 2", "Option 3"),
        onSelectionChanged = { }
      )
      Spacer.Vertical(16.dp)
      SegmentedControl(
        options = listOf("First", "Second", "Third"),
        onSelectionChanged = { }
      )
      Spacer.Vertical(16.dp)
      SegmentedControl(
        options = listOf("Поплака", "Об. место на настан", "ЈРМ"),
        onSelectionChanged = { }
      )
    }
  }
}
