package com.boryanz.upszakoni.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


import com.boryanz.upszakoni.utils.dismissState
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismiss(
    enableDismissing: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    dismissIcon: @Composable () -> Unit,
    onItemSwiped: () -> Unit
) {
    val dismissState = dismissState(onItemSwiped = onItemSwiped)
    SwipeToDismissBox(
        state = dismissState ,
        modifier = modifier,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = enableDismissing,
        backgroundContent = {
            if (dismissState.progress.absoluteValue < 0.8 && dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                DismissBackground(
                    value = dismissState.dismissDirection,
                    icon = { dismissIcon() }
                )
            }
        },
        content = {
            content()
        })
}