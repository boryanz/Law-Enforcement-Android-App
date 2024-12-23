package com.example.katasampleapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.katasampleapp.ui.theme.KataSampleAppTheme

object Texts {

    @Composable
    fun Info(
        text: String,
        textStyle: TextStyle = MaterialTheme.typography.labelSmall,
        modifier: Modifier = Modifier
    ) {
        Base(
            modifier = modifier,
            text = text,
            textStyle = textStyle
        )
    }

    @Composable
    fun Input(
        text: String,
        textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
        modifier: Modifier = Modifier
    ) {
        Base(
            modifier = modifier,
            text = text,
            textStyle = textStyle
        )
    }

    @Composable
    fun Paragraph(
        text: String,
        textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
        modifier: Modifier = Modifier
    ) {
        Base(
            modifier = modifier,
            text = text,
            textStyle = textStyle
        )
    }

    @Composable
    fun Title(
        text: String,
        textStyle: TextStyle = MaterialTheme.typography.titleMedium,
        fontWeight: FontWeight = FontWeight.Bold,
        modifier: Modifier = Modifier
    ) {
        Base(
            modifier = modifier,
            text = text,
            textStyle = textStyle,
            fontWeight = fontWeight
        )
    }

    @Composable
    fun SubHeadline(
        text: String,
        textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
        fontWeight: FontWeight = FontWeight.Bold,
        modifier: Modifier = Modifier
    ) {
        Base(
            modifier = modifier,
            text = text,
            textStyle = textStyle,
            fontWeight = fontWeight
        )
    }

    @Composable
    fun Headline(
        text: String,
        textStyle: TextStyle = MaterialTheme.typography.headlineLarge,
        fontWeight: FontWeight = FontWeight.ExtraBold,
        modifier: Modifier = Modifier
    ) {
        Base(
            modifier = modifier,
            text = text,
            textStyle = textStyle,
            fontWeight = fontWeight
        )
    }


    @Composable
    fun Base(
        text: String,
        textStyle: TextStyle,
        fontWeight: FontWeight = FontWeight.Normal,
        color: Color = Color.Unspecified,
        lineHeight: TextUnit = TextUnit.Unspecified,
        overflow: TextOverflow = TextOverflow.Clip,
        maxLines: Int = Int.MAX_VALUE,
        textAlign: TextAlign = TextAlign.Start,
        modifier: Modifier = Modifier
    ) {
        Text(
            text = text,
            style = textStyle,
            textAlign = textAlign,
            fontWeight = fontWeight
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TextsPreview() {
    KataSampleAppTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Texts.Info("Info")
            Spacer.Vertical(12.dp)
            Texts.Paragraph("Paragraph")
            Spacer.Vertical(12.dp)
            Texts.Input("Input")
            Spacer.Vertical(12.dp)
            Texts.SubHeadline("Subheadline")
            Spacer.Vertical(12.dp)
            Texts.Headline("Headline")
            Spacer.Vertical(12.dp)
            Texts.Title("Title")
        }
    }
}