package com.boryanz.upszakoni.ui.screens.crimequestions

import androidx.compose.runtime.Composable
import com.boryanz.upszakoni.data.model.TitleItem
import com.boryanz.upszakoni.ui.components.BasicTitleListScreen

@Composable
fun GoldenCrimeQuestionsScreen(
    topBarTitle: String,
    items: List<TitleItem>,
    onBackClicked: () -> Unit,
) {
    BasicTitleListScreen(
        topBarTitle = topBarTitle,
        items = items,
        onBackClicked = onBackClicked
    )
}