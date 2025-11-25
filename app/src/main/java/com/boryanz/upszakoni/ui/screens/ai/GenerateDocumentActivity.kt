package com.boryanz.upszakoni.ui.screens.ai

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.boryanz.upszakoni.ui.screens.ai.navigation.GenerateDocumentNavigationGraph
import com.boryanz.upszakoni.ui.theme.UpsTheme

class GenerateDocumentActivity : ComponentActivity() {

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, GenerateDocumentActivity::class.java)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      UpsTheme {
        val navHostController = rememberNavController()
        GenerateDocumentNavigationGraph(
          navHostController = navHostController,
          onBackClick = { finish() }
        )
      }
    }
  }
}