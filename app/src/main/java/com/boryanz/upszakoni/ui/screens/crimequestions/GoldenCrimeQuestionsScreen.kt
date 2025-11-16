package com.boryanz.upszakoni.ui.screens.crimequestions

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.boryanz.upszakoni.analytics.FirebaseAnalyticsManager
import com.boryanz.upszakoni.data.model.TitleItem
import com.boryanz.upszakoni.ui.components.BasicTitleListScreen

@Composable
fun GoldenCrimeQuestionsScreen(
  topBarTitle: String,
  items: List<TitleItem>,
  onBackClicked: () -> Unit,
) {

  LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
    FirebaseAnalyticsManager.logScreenEntry("Golden Crime Questions Screen")
  }

  BasicTitleListScreen(
    topBarTitle = topBarTitle,
    items = items,
    onBackClicked = onBackClicked
  )
}