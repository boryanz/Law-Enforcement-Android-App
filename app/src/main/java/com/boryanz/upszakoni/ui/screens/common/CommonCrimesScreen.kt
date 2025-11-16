package com.boryanz.upszakoni.ui.screens.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.data.model.Category
import com.boryanz.upszakoni.ui.components.CategoryHeader
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.ItemWithDescription
import com.boryanz.upszakoni.ui.components.UpsScaffold

typealias PdfData = (String, List<Int>) -> Unit

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommonOffensesAndCrimes(
    title: String,
    commonCrimesItems: List<Category>,
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
                commonCrimesItems.forEach {
                    stickyHeader {
                        CategoryHeader(it.name)
                    }
                    items(it.items) { item ->
                        ItemWithDescription(
                            isEnabled = true,
                            title = item.title,
                            description = item.description,
                            onClick = { /* TODO */ }
                        )
                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
        }
    }
}