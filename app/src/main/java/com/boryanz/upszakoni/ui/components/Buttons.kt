package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.theme.Base100
import com.boryanz.upszakoni.ui.theme.BaseContent1
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme

object Button {

    @Composable
    fun Primary(
        title: String,
        modifier: Modifier = Modifier,
        isEnabled: Boolean = true,
        onClick: () -> Unit,
    ) {
        Button(
            modifier = modifier
                .fillMaxWidth()
                .height(52.dp),
            onClick = onClick,
            enabled = isEnabled,
            colors = ButtonColors(
                containerColor = BaseContent1,
                contentColor = Base100,
                disabledContentColor = Base100,
                disabledContainerColor = Color.Gray,
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ButtonsPreview() {
    KataSampleAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button.Primary("Primary button", onClick = {})
            Spacer.Vertical(6.dp)
            Button.Primary("Primary button", onClick = {}, isEnabled = false)
            Spacer.Vertical(6.dp)
        }
    }
}