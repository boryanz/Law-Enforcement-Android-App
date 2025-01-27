package com.boryanz.upszakoni.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme
import com.boryanz.upszakoni.ui.theme.Typography

@Composable
fun TitleItem(
    title: String,
    isEnabled: Boolean = false,
    onClick: () -> Unit
) {
    RowItem(isEnabled = isEnabled, onClick = onClick) {
        Text(text = title, textAlign = TextAlign.Start, style = Typography.bodyLarge)
    }
}


@Preview(showBackground = true)
@Composable
private fun PoliceAuthorityPreview() {
    KataSampleAppTheme {
        TitleItem("Приведување", onClick = {})
    }
}