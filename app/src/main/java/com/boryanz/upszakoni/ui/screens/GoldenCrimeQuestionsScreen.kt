package com.boryanz.upszakoni.ui.screens

import androidx.compose.runtime.Composable
import com.boryanz.upszakoni.data.model.TitleItem
import com.boryanz.upszakoni.ui.components.BasicTitleListScreen

@Composable
fun GoldenCrimeQuestionsScreen(topBarTitle: String, items: List<TitleItem>) {
    BasicTitleListScreen(topBarTitle, items)
}