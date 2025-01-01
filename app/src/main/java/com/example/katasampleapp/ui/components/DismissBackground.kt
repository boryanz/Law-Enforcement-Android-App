package com.example.katasampleapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(
    value: SwipeToDismissBoxValue,
    icon: @Composable () -> Unit,
) {
    val color = when (value) {
        SwipeToDismissBoxValue.StartToEnd -> Color.Transparent /*Change as per needs*/
        SwipeToDismissBoxValue.EndToStart -> Color.Transparent /*Change as per needs*/
        SwipeToDismissBoxValue.Settled -> Color.Transparent /*Change as per needs*/
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color, shape = RoundedCornerShape(4.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        icon()
    }
}