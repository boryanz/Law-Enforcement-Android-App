package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard

import androidx.compose.runtime.Composable
import com.boryanz.upszakoni.data.model.TitleItem
import com.boryanz.upszakoni.ui.components.BasicTitleListScreen

@Composable
fun NonWorkingDaysInfoScreen(
    content: List<TitleItem>,
    onBackClicked: () -> Unit
) {
    BasicTitleListScreen(
        topBarTitle = "Неработни денови",
        items = content,
        onBackClicked = onBackClicked
    )
}