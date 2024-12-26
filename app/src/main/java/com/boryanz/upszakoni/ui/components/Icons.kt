package com.boryanz.upszakoni.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

object Icons {

    @Composable
    fun Back(
        onClick: () -> Unit,
        tint: Color = LocalContentColor.current,
    ) {
        Base(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            tint = tint,
            onClick = onClick
        )
    }

    @Composable
    fun Base(
        imageVector: ImageVector,
        tint: Color = LocalContentColor.current,
        onClick: () -> Unit,
    ) {
        IconButton(onClick = onClick, content = {
            Icon(
                imageVector = imageVector,
                tint = tint,
                contentDescription = "base icon"
            )
        })
    }
}