package com.boryanz.upszakoni.ui.screens.archivedlaws

import android.widget.Toast
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.SwipeToDismiss
import com.boryanz.upszakoni.ui.components.TitleItem
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.screens.common.ScreenAction

@Composable
fun ArchivedLawsScreen(
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    onBackClicked: () -> Unit,
    viewModel: ArchivedLawsViewModel = viewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.onUiEvent(ScreenAction.GetLaws(context))
    }
    UpsScaffold(
        topBarTitle = { Text(text = "Архива на закони") },
        navigationIcon = {
            Icons.Back(onClick = onBackClicked)
        },
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val lazyScrollState = rememberLazyListState()

        LazyColumn(modifier = modifier
            .padding(paddingValues)
            .padding(8.dp)) {
            items(
                items = uiState.laws,
                key = { it }) {
                SwipeToDismiss(
                    content = {
                        TitleItem(
                            isEnabled = true,
                            title = it,
                            onClick = { onItemClick(it) }
                        )
                    },
                    dismissIcon = {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.AddHome,
                            contentDescription = "Home"
                        )
                    },
                    onItemSwiped = {
                        viewModel.onUiEvent(ScreenAction.LawSwiped(context, it))
                        Toast.makeText(
                            /* context = */ context,
                            /* text = */ "Законот вратен на почетна",
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