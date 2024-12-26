package com.boryanz.upszakoni.ui.screens

import androidx.compose.runtime.Composable
import com.boryanz.upszakoni.data.model.TitleItem
import com.boryanz.upszakoni.ui.components.BasicTitleListScreen

@Composable
fun PoliceAuthoritiesScreen(
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