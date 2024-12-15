package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.theme.BaseContent
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme

@Composable
fun GridLayout(items: List<String>){

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),

        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(items.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
                        .border(1.dp, color = BaseContent),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = items[index],
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    )
}


@Preview(showBackground = false)
@Composable
private fun LazyVerticalGridPreview() {
    KataSampleAppTheme {
        GridLayout(listOf("emenge", "kijamariten", "Dzianengoro", "bla blsa"))
    }
}
