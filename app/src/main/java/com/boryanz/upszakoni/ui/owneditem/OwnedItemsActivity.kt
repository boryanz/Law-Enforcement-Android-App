package com.boryanz.upszakoni.ui.owneditem

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.boryanz.upszakoni.ui.navigation.navgraph.OwnedItemsNavigationGraph
import com.boryanz.upszakoni.ui.theme.UpsTheme

class OwnedItemsActivity : ComponentActivity() {

  companion object {
    fun createIntent(context: Context) = Intent(context, OwnedItemsActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      UpsTheme {
        OwnedItemsNavigationGraph(
          onBackNavigated = { finish() }
        )
      }
    }
  }
}
