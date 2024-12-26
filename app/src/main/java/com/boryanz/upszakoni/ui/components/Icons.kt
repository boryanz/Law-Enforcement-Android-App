package com.boryanz.upszakoni.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.boryanz.upszakoni.ui.theme.Base100

object Icons {

    @Composable
    fun Back(
        onClick: () -> Unit,
    ) {
        Base(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = onClick
        )
    }

    @Composable
    fun Base(
        imageVector: ImageVector,
        onClick: () -> Unit,
    ) {
        IconButton(onClick = onClick, content = {
            Icon(
                imageVector = imageVector,
                tint = Base100,
                contentDescription = "base icon"
            )
        })
    }
}