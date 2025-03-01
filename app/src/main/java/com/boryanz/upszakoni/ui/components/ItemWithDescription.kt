package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.theme.UpsTheme
import com.boryanz.upszakoni.ui.theme.Typography

@Composable
fun ItemWithDescription(
    isEnabled: Boolean = false,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    RowItem(isEnabled = isEnabled, onClick = onClick) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, style = Typography.titleMedium)
            Spacer.Vertical(2.dp)
            Text(text = description, style = Typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemWithDescriptionPreview() {
    UpsTheme {
        ItemWithDescription(
            isEnabled = true,
            title = "Тепачка",
            description = "член 13 од ЗППЈРМ",
            onClick = {})
    }
}