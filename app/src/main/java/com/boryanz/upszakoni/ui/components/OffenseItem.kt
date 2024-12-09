package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme
import com.boryanz.upszakoni.ui.theme.Typography

@Composable
fun OffenseItem(title: String, description: String) {
    RowItem(isEnabled = false, onClick = {}) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, style = Typography.titleLarge)
            Spacer.Vertical(2.dp)
            Text(text = description, style = Typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OffenseItemPreview() {
    KataSampleAppTheme {
        OffenseItem(title = "Тепачка", description = "член 13 од ЗППЈРМ")
    }
}