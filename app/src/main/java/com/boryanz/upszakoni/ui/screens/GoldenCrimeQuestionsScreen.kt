package com.boryanz.upszakoni.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.boryanz.upszakoni.data.model.TitleItem
import com.boryanz.upszakoni.ui.components.BasicTitleListScreen

@Composable
fun GoldenCrimeQuestionsScreen(topBarTitle: String, items: List<TitleItem>) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("ddddddd")
        BasicTitleListScreen(topBarTitle, items)
    }
}