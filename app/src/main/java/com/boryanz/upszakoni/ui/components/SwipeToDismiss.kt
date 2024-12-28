package com.boryanz.upszakoni.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismiss(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    onItemSwiped: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> { /* do nothing */ }

                SwipeToDismissBoxValue.EndToStart -> {
                    onItemSwiped()
                }

                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        // positional threshold of 25%
        positionalThreshold = { it * .80f }
    )
    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            if (dismissState.progress.absoluteValue > 0.2 && dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                DismissBackground(dismissState = dismissState)
            }
        },
        content = {
            content()
        })
}