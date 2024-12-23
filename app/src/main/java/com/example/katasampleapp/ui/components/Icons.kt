package com.example.katasampleapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
    fun Menu(
        onClick: () -> Unit,
        tint: Color = LocalContentColor.current,
    ) {
        Base(
            imageVector = Icons.Filled.Menu,
            tint = tint,
            onClick = onClick
        )
    }

    @Composable
    fun Info(
        onClick: () -> Unit,
        tint: Color = LocalContentColor.current,
    ) {
        Base(
            imageVector = Icons.Filled.Info,
            tint = tint,
            onClick = onClick
        )
    }

    @Composable
    fun Close(
        onClick: () -> Unit,
        tint: Color = LocalContentColor.current,
    ) {
        Base(
            imageVector = Icons.Filled.Close,
            tint = tint,
            onClick = onClick
        )
    }

    @Composable
    fun Search(
        onClick: () -> Unit,
        tint: Color = LocalContentColor.current,
    ) {
        Base(
            imageVector = Icons.Filled.Search,
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
        Button.Icon(onClick = onClick) {
            Icon(
                imageVector = imageVector,
                tint = tint,
                contentDescription = "base icon"
            )
        }
    }
}