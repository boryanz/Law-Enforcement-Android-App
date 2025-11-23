package com.boryanz.upszakoni.ui.screens.ai

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
data object PromptInformationDestination

@Serializable
data object AddPromptDestination

@Serializable
data object DocumentDestination

@Composable
fun GenerateDocumentNavigation(
  navHostController: NavHostController = rememberNavController(),
  onBackClick: () -> Unit
) {
  val generatedDocument = remember { mutableStateOf("") }

  NavHost(
    navController = navHostController,
    startDestination = PromptInformationDestination,
  ) {
    composable<PromptInformationDestination> {
      PromptInformationScreen(
        onContinueClicked = {
          navHostController.navigate(AddPromptDestination)
        },
        onBackClicked = onBackClick
      )
    }

    composable<AddPromptDestination> {
      AddPromptScreen(
        onBackClicked = { navHostController.navigateUp() },
        onDocumentGenerated = { document ->
          generatedDocument.value = document
          navHostController.navigate(DocumentDestination)
        }
      )
    }

    composable<DocumentDestination> {
      DocumentScreen(
        document = generatedDocument.value,
        onBackClicked = { navHostController.navigateUp() }
      )
    }
  }
}
