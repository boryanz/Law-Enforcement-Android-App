package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.data.model.TitleItem
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme

@Composable
fun LazyGridLayout() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = WindowInsets.systemBars.asPaddingValues()
    ) {
        items(monthsInCalendar) { month ->
            RowItem(
                horizontalArrangement = Arrangement.Center,
                onClick = {},
                content = {
                    Text(month.title, textAlign = TextAlign.Center)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LazyGridLayoutPreview() {
    KataSampleAppTheme {
        LazyGridLayout()
    }
}

val monthsInCalendar = listOf(
    TitleItem("Јануари"),
    TitleItem("Февруари"),
    TitleItem("Март"),
    TitleItem("Април"),
    TitleItem("Мај"),
    TitleItem("Јуни"),
    TitleItem("Јули"),
    TitleItem("Август"),
    TitleItem("Септември"),
    TitleItem("Октомври"),
    TitleItem("Ноември"),
    TitleItem("Декември"),
)