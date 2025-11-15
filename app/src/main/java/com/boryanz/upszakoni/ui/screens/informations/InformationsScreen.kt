package com.boryanz.upszakoni.ui.screens.informations

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.BasicTitleListScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun InformationScreen(onBackClicked: () -> Unit) {
    val viewModel = koinViewModel<InformationScreenViewModel>()
    BasicTitleListScreen(
        items = viewModel.information,
        topBarTitle = stringResource(R.string.informations_screen_title),
        onBackClicked = onBackClicked
    )
}