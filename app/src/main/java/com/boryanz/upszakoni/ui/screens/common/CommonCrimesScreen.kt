package com.boryanz.upszakoni.ui.screens.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.data.crimesItems
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.TitleItem
import com.boryanz.upszakoni.ui.components.UpsScaffold


enum class OffenseLaw(val lawId: String, val title: String) {
  TRAFFIC(lawId = "traffic", title = "ЗБСП"),
  ECOLOGY(lawId = "ecology", "Закон за јавна чистота"),
  VEHICLE(lawId = "vehicle", "Закон за возила"),
  WEAPON(lawId = "weapon", "Закон за оружје"),
  PUBLIC_PEACE(lawId = "public-peace", title = "ЗППЈРМ"),
  OTHER(lawId = "other", title = "Останати")
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OffensesOverviewScreen(
  onItemClicked: (law: OffenseLaw) -> Unit,
  onBackClicked: (() -> Unit)? = null,
) {
  UpsScaffold(
    topBarTitle = {
      Text(
        text = stringResource(R.string.offenses_title),
        fontWeight = FontWeight.Bold
      )
    },
    navigationIcon = if (onBackClicked != null) {
      { Icons.Back(onClick = onBackClicked) }
    } else null
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Top
    ) {
      TitleItem(
        title = stringResource(R.string.traffic_offense_law),
        onClick = { onItemClicked(OffenseLaw.TRAFFIC) })
      Spacer.Vertical(4.dp)
      TitleItem(
        title = stringResource(R.string.vehicle_offense_law),
        onClick = { onItemClicked(OffenseLaw.VEHICLE) })
      Spacer.Vertical(4.dp)
      TitleItem(
        title = stringResource(R.string.ecology_offense_law),
        onClick = { onItemClicked(OffenseLaw.ECOLOGY) })
      Spacer.Vertical(4.dp)
      TitleItem(
        title = stringResource(R.string.public_peace_offense_law),
        onClick = { onItemClicked(OffenseLaw.PUBLIC_PEACE) })
      TitleItem(
        title = stringResource(R.string.weapons_offense_law),
        onClick = { onItemClicked(OffenseLaw.WEAPON) })
      TitleItem(
        title = stringResource(R.string.others_offense_law),
        onClick = { onItemClicked(OffenseLaw.OTHER) })
    }
  }
}

@Composable
fun CommonCrimesScreen(
  onBackClicked: () -> Unit,
) {
  UpsScaffold(
    topBarTitle = {
      Text(
        text = stringResource(R.string.crimes_title),
        fontWeight = FontWeight.Bold
      )
    },
    navigationIcon = { Icons.Back(onClick = onBackClicked) }
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(8.dp),
    ) {
      crimesItems.forEach { category ->
        item {
          Text(
            text = category.name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
          )
        }
        items(category.items) { offense ->
          OffenseItem(
            article = offense.title,
            description = offense.description
          )
          Spacer.Vertical(4.dp)
        }
      }
    }
  }
}