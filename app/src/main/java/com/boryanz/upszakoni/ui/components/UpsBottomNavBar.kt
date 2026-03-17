package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

enum class BottomNavItem { Laws, Overtime, Equipment }

@Composable
fun UpsBottomNavBar(
  selected: BottomNavItem,
  onLawsClicked: () -> Unit,
  onOvertimeClicked: () -> Unit,
  onEquipmentClicked: () -> Unit,
) {
  NavigationBar {
    NavigationBarItem(
      selected = selected == BottomNavItem.Laws,
      onClick = onLawsClicked,
      icon = { Icon(imageVector = Icons.Filled.Gavel, contentDescription = null) },
      label = { Text("Закони") }
    )
    NavigationBarItem(
      selected = selected == BottomNavItem.Overtime,
      onClick = onOvertimeClicked,
      icon = { Icon(imageVector = Icons.Filled.Timelapse, contentDescription = null) },
      label = { Text("Прекувремени") }
    )
    NavigationBarItem(
      selected = selected == BottomNavItem.Equipment,
      onClick = onEquipmentClicked,
      icon = { Icon(imageVector = Icons.Filled.Inventory, contentDescription = null) },
      label = { Text("Опрема") }
    )
  }
}

@Composable
fun BottomNavWrapper(
  selected: BottomNavItem,
  onLawsClicked: () -> Unit,
  onOvertimeClicked: () -> Unit,
  onEquipmentClicked: () -> Unit,
  content: @Composable () -> Unit,
) {
  Column(Modifier.fillMaxSize()) {
    Box(
      modifier = Modifier
        .weight(1f)
        .consumeWindowInsets(WindowInsets.navigationBars),
    ) {
      content()
    }
    UpsBottomNavBar(
      selected = selected,
      onLawsClicked = onLawsClicked,
      onOvertimeClicked = onOvertimeClicked,
      onEquipmentClicked = onEquipmentClicked,
    )
  }
}
