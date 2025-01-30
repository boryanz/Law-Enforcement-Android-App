package com.boryanz.upszakoni.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.RoundaboutLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.boryanz.upszakoni.ui.theme.Base100

object Icons {

    @Composable
    fun Back(
        tint: Color = Base100,
        onClick: () -> Unit,
    ) {
        Base(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            tint = tint,
            onClick = onClick
        )
    }

    @Composable
    fun Archive(
        tint: Color = Base100,
        onClick: () -> Unit
    ) {
        Base(
            imageVector = Icons.Default.Archive,
            tint = tint,
            onClick = onClick
        )
    }

    @Composable
    fun Delete(
        tint: Color = Base100,
        onClick: () -> Unit
    ) {
        Base(
            imageVector = Icons.Default.DeleteOutline,
            tint = tint,
            onClick = onClick
        )
    }

    @Composable
    fun Undo(
        tint: Color = Base100,
        onClick: () -> Unit
    ) {
        Base(
            imageVector = Icons.Default.RoundaboutLeft,
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