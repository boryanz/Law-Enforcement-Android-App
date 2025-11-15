package com.boryanz.upszakoni.ui.screens.bonussalary.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.data.model.TitleItem
import com.boryanz.upszakoni.ui.components.BasicTitleListScreen

@Composable
fun NonWorkingDaysInfoScreen(
    content: List<TitleItem>,
    onBackClicked: () -> Unit
) {
    BasicTitleListScreen(
        topBarTitle = stringResource(R.string.non_working_days_title),
        items = content,
        onBackClicked = onBackClicked
    )
}