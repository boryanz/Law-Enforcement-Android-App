package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boryanz.upszakoni.data.model.TitleItem
import com.boryanz.upszakoni.ui.components.input.TextFieldInput
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme

@Composable
fun LazyGridLayout(
    paddingValues: PaddingValues,
    onClick: (String) -> Unit,
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = paddingValues
    ) {
        items(monthsInCalendar) { month ->
            TextFieldInput.BaseOutline(
                modifier = Modifier
                    .padding(4.dp)
                    .focusable(enabled = false),
                textStyle = TextStyle(textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 17.sp),
                isReadOnly = true,
                interactionSource = remember { MutableInteractionSource() }
                    .also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Release) {
                                    onClick(month.title)
                                }
                            }
                        }
                    },
                labelText = month.title,
                value = "10",
                onValueChanged = { /* Do nothing as it's read only */ },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LazyGridLayoutPreview() {
    KataSampleAppTheme {
        LazyGridLayout(PaddingValues(), {})
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