package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boryanz.upszakoni.ui.theme.BaseContent

@Composable
fun CategoryHeader(name: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = BaseContent, shape = RoundedCornerShape(4.dp))
            .padding(8.dp),
    ) {
        Text(
            text = name,
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}