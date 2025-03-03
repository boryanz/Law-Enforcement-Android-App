package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.theme.UpsTheme
import com.boryanz.upszakoni.ui.theme.Typography

@Composable
fun TitleItem(
    title: String,
    isEnabled: Boolean = false,
    onClick: () -> Unit
) {
    RowItem(isEnabled = isEnabled, onClick = onClick) {
        Text(modifier = Modifier.fillMaxWidth().padding(8.dp),text = title, textAlign = TextAlign.Start, style = Typography.bodyLarge)
    }
}


@Preview(showBackground = true)
@Composable
private fun PoliceAuthorityPreview() {
    UpsTheme {
        TitleItem("Приведување", onClick = {})
    }
}