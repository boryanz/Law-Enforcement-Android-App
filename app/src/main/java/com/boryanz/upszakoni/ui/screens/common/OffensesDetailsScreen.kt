package com.boryanz.upszakoni.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.domain.BaseError
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.utils.collectEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun OffensesDetailsScreen(
  lawId: String,
  title: String,
  onBackClicked: () -> Unit,
  onFailure: (BaseError) -> Unit
) {
  val viewModel = koinViewModel<OffenseViewModel>()
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
    viewModel.getOffenses(lawId)
  }

  viewModel.uiEvent.collectEvents { event ->
    when (event) {
      is OffenseUiEvent.Failure -> onFailure(event.baseError)
    }
  }

  OffensesDetailsContent(
    title = title,
    uiState = uiState,
    onBackClicked = onBackClicked
  )
}

@Composable
fun OffensesDetailsContent(
  title: String,
  uiState: OffensesUiState,
  onBackClicked: () -> Unit,
) {
  UpsScaffold(
    topBarTitle = { Text(text = title, fontWeight = FontWeight.Bold) },
    navigationIcon = {
      Icons.Back(onClick = onBackClicked)
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Top
    ) {
      LazyColumn {
        items(uiState.offenses) { item ->
          OffenseItem(
            article = item.title,
            description = item.description
          )
          Spacer.Vertical(4.dp)
        }
      }
    }
  }
}