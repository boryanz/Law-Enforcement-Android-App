package com.boryanz.upszakoni.ui.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.boryanz.upszakoni.data.local.database.model.owneditem.ItemCategory
import com.boryanz.upszakoni.ui.navigation.destinations.OwnedItemScreenDestination
import com.boryanz.upszakoni.ui.navigation.destinations.OwnedItemsListScreenDestination
import com.boryanz.upszakoni.ui.owneditem.addowneditem.OwnedItemScreen
import com.boryanz.upszakoni.ui.owneditem.overview.OwnedItemsListScreen
import com.boryanz.upszakoni.utils.noEnterTransition
import com.boryanz.upszakoni.utils.noExitTransition

@Composable
fun OwnedItemsNavigationGraph(
  onBackNavigated: () -> Unit,
) {
  val navController = rememberNavController()

  NavHost(
    navController = navController,
    startDestination = OwnedItemsListScreenDestination,
    enterTransition = noEnterTransition,
    exitTransition = noExitTransition
  ) {
    composable<OwnedItemsListScreenDestination> {
      OwnedItemsListScreen(
        onBackClicked = onBackNavigated,
        onItemClick = { item ->
          navController.navigate(
            OwnedItemScreenDestination(
              itemId = item.id,
              itemName = item.name,
              volume = item.volume,
              category = item.category
            )
          )
        },
        onAddItemClicked = {
          navController.navigate(
            OwnedItemScreenDestination(
              itemId = 0,
              itemName = "",
              volume = 0,
              category = ItemCategory.OTHER
            )
          )
        }
      )
    }

    composable<OwnedItemScreenDestination> {
      val route = it.toRoute<OwnedItemScreenDestination>()
      OwnedItemScreen(
        itemId = route.itemId,
        itemName = route.itemName,
        volume = route.volume,
        category = route.category,
        onBackClicked = { navController.navigateUp() }
      )
    }
  }
}
