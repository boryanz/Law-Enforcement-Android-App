package com.example.katasampleapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.katasampleapp.ui.theme.KataSampleAppTheme

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
            shape = RoundedCornerShape(4.dp)
        ) {
            Texts.Title(title)
        }
    }

    @Composable
    fun Secondary(
        title: String,
        modifier: Modifier = Modifier,
        isEnabled: Boolean = true,
        onClick: () -> Unit,
    ) {
        OutlinedButton(
            modifier = modifier
                .fillMaxWidth()
                .height(52.dp),
            onClick = onClick,
            enabled = isEnabled,
            shape = RoundedCornerShape(4.dp)
        ) {
            Texts.Title(title)
        }
    }

    @Composable
    fun Icon(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        content: @Composable () -> Unit,
    ) {
        IconButton(onClick = onClick, content = content, enabled = enabled, modifier = modifier)
    }
}


@Preview(showBackground = true)
@Composable
private fun ButtonsPreview() {
    KataSampleAppTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button.Primary("Primary button", onClick = {})
            Spacer.Vertical(6.dp)
            Button.Secondary("Secondary") { }
            Spacer.Vertical(6.dp)
            Button.Icon(onClick = {}) {
                Icon(imageVector = Icons.Filled.AccessAlarm, contentDescription = null)
            }
        }
    }
}