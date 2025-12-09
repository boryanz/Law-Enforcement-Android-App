package com.boryanz.upszakoni.ui.screens.laws

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.data.NavigationDrawerDestination
import com.boryanz.upszakoni.ui.components.NavigationDrawer
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.SwipeToDismiss
import com.boryanz.upszakoni.ui.components.TitleItem
import com.boryanz.upszakoni.ui.screens.common.ScreenAction
import org.koin.androidx.compose.koinViewModel

@Composable
fun LawsScreen(
  onLawClick: (String) -> Unit,
  onItemClick: (NavigationDrawerDestination) -> Unit,
  onArchivedLawsClicked: () -> Unit,
  onShareAppClicked: () -> Unit,
  onAppUpdateClicked: () -> Unit,
  onFeedbackFormClicked: () -> Unit
) {
  val viewModel = koinViewModel<LawsViewModel>()
  val featureFlagsState by viewModel.featureFlagsState.collectAsStateWithLifecycle()
  val context = LocalContext.current

  LaunchedEffect(Unit) {
    viewModel.onUiEvent(ScreenAction.GetLaws)
  }
  NavigationDrawer(
    screenTitle = stringResource(R.string.laws_screen_title),
    onItemClicked = { onItemClick(it) },
    onArchivedLawsClicked = onArchivedLawsClicked,
    featureFlags = featureFlagsState,
    onShareAppClicked = onShareAppClicked,
    onAppUpdateClicked = onAppUpdateClicked,
    onFeedbackFormClicked = onFeedbackFormClicked
  ) { paddingValues ->
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Top
    ) {
      OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = searchQuery,
        onValueChange = {
          searchQuery = it
        },
        label = { Text(stringResource(R.string.laws_search_label)) },
        trailingIcon = {
          com.boryanz.upszakoni.ui.components.Icons.Base(
            imageVector = Icons.Outlined.Search,
            onClick = {})
        }
      )
      Spacer(modifier = Modifier.padding(vertical = 4.dp))
      LazyColumn {
        items(
          uiState.laws.filter { it.title.contains(searchQuery, ignoreCase = true) },
          key = { it.title }) {
          SwipeToDismiss(
            content = {
              TitleItem(
                isEnabled = true,
                title = it.title,
                onClick = { onLawClick(it.id) })
            },
            onItemSwiped = {
              viewModel.onUiEvent(ScreenAction.LawSwiped(it.title))
              Toast.makeText(context, R.string.laws_archived_message, Toast.LENGTH_SHORT)
                .show()

            },
            dismissIcon = {
              Icon(
                imageVector = Icons.Default.Archive,
                contentDescription = stringResource(R.string.laws_archive_content_description)
              )
            },
            enableDismissing = !lazyListState.isScrollInProgress
          )
          Spacer.Vertical(2.dp)
        }
      }
    }
  }
}
