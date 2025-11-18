package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boryanz.upszakoni.ui.theme.Base100
import com.boryanz.upszakoni.ui.theme.BaseContent1
import com.boryanz.upszakoni.ui.theme.UpsTheme

object NumberPicker {

  @Composable
  fun Simple(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    minValue: Int = 0,
    maxValue: Int = 100,
    label: String? = null
  ) {
    val isDarkMode = isSystemInDarkTheme()
    val textColor = if (isDarkMode) Base100 else BaseContent1
    val disabledColor = textColor.copy(alpha = 0.5f)

    Column(
      modifier = modifier,
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      if (label != null) {
        Text(
          text = label,
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSurface,
        )
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .border(width = 1.dp, color = textColor, RoundedCornerShape(4.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Filled.Remove,
          contentDescription = "Decrease",
          modifier = Modifier
            .size(48.dp)
            .clickable(enabled = value > minValue) {
              onValueChange(value - 1)
            }
            .padding(12.dp),
          tint = if (value > minValue) textColor else disabledColor
        )

        Text(
          text = value.toString(),
          style = MaterialTheme.typography.headlineSmall.copy(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
          ),
          color = textColor,
          textAlign = TextAlign.Center,
          modifier = Modifier
            .weight(1f)
            .padding(12.dp),
          maxLines = 1
        )

        Icon(
          imageVector = Icons.Filled.Add,
          contentDescription = "Increase",
          modifier = Modifier
            .size(48.dp)
            .clickable(enabled = value < maxValue) {
              onValueChange(value + 1)
            }
            .padding(12.dp),
          tint = if (value < maxValue) textColor else disabledColor
        )
      }
    }
  }

  @Composable
  fun Compact(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    minValue: Int = 0,
    maxValue: Int = 100
  ) {
    val isDarkMode = isSystemInDarkTheme()
    val textColor = if (isDarkMode) Base100 else BaseContent1
    val disabledColor = textColor.copy(alpha = 0.5f)

    Row(
      modifier = modifier
        .height(44.dp)
        .border(width = 1.dp, color = textColor, RoundedCornerShape(4.dp))
        .padding(4.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.Filled.Remove,
        contentDescription = "Decrease",
        modifier = Modifier
          .size(36.dp)
          .clickable(enabled = value > minValue) {
            onValueChange(value - 1)
          }
          .padding(8.dp),
        tint = if (value > minValue) textColor else disabledColor
      )

      Text(
        text = value.toString(),
        style = MaterialTheme.typography.labelLarge.copy(
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold
        ),
        color = textColor,
        textAlign = TextAlign.Center,
        modifier = Modifier
          .weight(1f)
          .padding(horizontal = 12.dp)
      )

      Icon(
        imageVector = Icons.Filled.Add,
        contentDescription = "Increase",
        modifier = Modifier
          .size(36.dp)
          .clickable(enabled = value < maxValue) {
            onValueChange(value + 1)
          }
          .padding(8.dp),
        tint = if (value < maxValue) textColor else disabledColor
      )
    }
  }
}

@Preview(showBackground = true)
@PreviewLightDark
@Composable
private fun NumberPickerPreview() {
  UpsTheme {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp),
      verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
      val (simpleValue, setSimpleValue) = androidx.compose.runtime.remember {
        androidx.compose.runtime.mutableStateOf(50)
      }

      NumberPicker.Simple(
        value = simpleValue,
        onValueChange = setSimpleValue,
        minValue = 0,
        maxValue = 100,
        label = "Select Hours"
      )

      Spacer.Vertical(16.dp)

      val (compactValue, setCompactValue) = androidx.compose.runtime.remember {
        androidx.compose.runtime.mutableStateOf(25)
      }

      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
          text = "Compact Variant",
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSurface
        )
        NumberPicker.Compact(
          value = compactValue,
          onValueChange = setCompactValue,
          minValue = 0,
          maxValue = 50,
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}
