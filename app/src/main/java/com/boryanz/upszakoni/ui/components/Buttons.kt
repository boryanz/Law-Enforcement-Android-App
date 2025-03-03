package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Sick
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.theme.Base100
import com.boryanz.upszakoni.ui.theme.BaseContent1
import com.boryanz.upszakoni.ui.theme.UpsTheme

object Button {

    @Composable
    fun SwitchWithIcon(
        title: String,
        imageVector: ImageVector,
        isChecked: Boolean,
        onClick: (Boolean) -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .border(width = 1.dp, color = LocalContentColor.current, RoundedCornerShape(4.dp))
                .padding(8.dp)
                .clickable(enabled = true, onClick = { onClick(!isChecked) }),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                tint = Base100,
                imageVector = imageVector,
                contentDescription = ""
            )
            Text(text = title)
            androidx.compose.material3.Switch(
                modifier = Modifier,
                checked = isChecked,
                onCheckedChange = { onClick(it) }
            )
        }
    }

    @Composable
    fun PrimaryOutlinedWithIcon(
        title: String,
        imageVector: ImageVector,
        modifier: Modifier = Modifier,
        isEnabled: Boolean = true,
        onClick: () -> Unit
    ) {
        OutlinedButton(
            modifier = modifier
                .fillMaxWidth()
                .height(52.dp),
            onClick = onClick,
            enabled = isEnabled,
            shape = RoundedCornerShape(4.dp)
        ) {
            Icon(imageVector, "")
            Spacer.Horizontal(8.dp)
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

    @Composable
    fun Outlined(
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
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

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
@PreviewLightDark
@Composable
private fun ButtonsPreview() {
    UpsTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button.Primary("Primary button", onClick = {})
            Spacer.Vertical(6.dp)
            Button.Primary("Primary button", onClick = {}, isEnabled = false)
            Spacer.Vertical(6.dp)
            Button.Outlined("Outlined button", onClick = {})
            Spacer.Vertical(6.dp)
            Button.PrimaryOutlinedWithIcon(
                title = "Primary with icon",
                imageVector = Icons.Filled.Delete,
                onClick = {}
            )
            Spacer.Vertical(6.dp)
            Button.SwitchWithIcon(
                title = "Боледување",
                imageVector = Icons.Filled.Sick,
                isChecked = true,
                onClick = {}
            )
            Spacer.Vertical(6.dp)
            Button.SwitchWithIcon(
                title = "Платено отсуство",
                imageVector = Icons.Filled.AirplanemodeActive,
                isChecked = false,
                onClick = {}
            )
        }
    }
}