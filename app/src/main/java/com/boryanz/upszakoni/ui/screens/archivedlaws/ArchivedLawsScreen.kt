package com.boryanz.upszakoni.ui.screens.archivedlaws

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.SwipeToDismiss
import com.boryanz.upszakoni.ui.components.TitleItem
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArchivedLawsScreen(
  onItemClick: (String) -> Unit,
  onBackClicked: () -> Unit,
) {
  val viewModel = koinViewModel<ArchivedLawsViewModel>()
  val context = LocalContext.current
  LaunchedEffect(Unit) {
    viewModel.onUiEvent(ScreenAction.GetLaws)
  }
  UpsScaffold(
    topBarTitle = { Text(text = stringResource(R.string.archived_laws_title)) },
    navigationIcon = {
      Icons.Back(onClick = onBackClicked)
    },
  ) { paddingValues ->
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyScrollState = rememberLazyListState()

    if (uiState.laws.isEmpty()) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues)
          .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      )
      {
        Text(stringResource(R.string.archived_laws_empty_state))
      }
    } else {
      LazyColumn(
        modifier = Modifier
          .padding(paddingValues)
          .padding(8.dp)
      ) {
        items(
          items = uiState.laws,
          key = { it }) {
          SwipeToDismiss(
            content = {
              TitleItem(
                isEnabled = true,
                title = it,
                onClick = { onItemClick("$it.pdf") }
              )
            },
            dismissIcon = {
              Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.AddHome,
                contentDescription = stringResource(R.string.archived_laws_restore_icon_description)
              )
            },
            onItemSwiped = {
              viewModel.onUiEvent(ScreenAction.LawSwiped(it))
              Toast.makeText(
                /* context = */ context,
                /* text = */ R.string.archived_laws_restore_message,
                /* duration = */ Toast.LENGTH_SHORT
              ).show()
            },
            enableDismissing = !lazyScrollState.isScrollInProgress
          )
          Spacer.Vertical(2.dp)
        }
      }
    }
  }
}