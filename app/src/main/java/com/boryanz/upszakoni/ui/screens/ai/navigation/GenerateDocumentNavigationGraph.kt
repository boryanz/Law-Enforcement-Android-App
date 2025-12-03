package com.boryanz.upszakoni.ui.screens.ai.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.boryanz.upszakoni.ui.screens.ai.addprompt.AddPromptScreen
import com.boryanz.upszakoni.ui.screens.ai.document.DocumentScreen
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryDestination
import com.boryanz.upszakoni.ui.screens.ai.history.DocumentHistoryScreen
import com.boryanz.upszakoni.ui.screens.ai.information.PromptInformationScreen
import kotlinx.serialization.Serializable

@Serializable
data object PromptInformationDestination

@Serializable
data object AddPromptDestination

@Serializable
data class DocumentDestination(val fullPrompt: String, val examplePrompt: String)

@Composable
fun GenerateDocumentNavigationGraph(
  navHostController: NavHostController = rememberNavController(),
  onBackClick: () -> Unit
) {

  NavHost(
    navController = navHostController,
    startDestination = PromptInformationDestination,
  ) {
    composable<PromptInformationDestination> {
      PromptInformationScreen(
        onContinueClicked = { navHostController.navigate(AddPromptDestination) },
        onBackClicked = onBackClick
      )
    }

    composable<DocumentHistoryDestination> {
      DocumentHistoryScreen(
        onBackClicked = { navHostController.navigateUp() },
        onAddDocumentClicked = { navHostController.navigate(AddPromptDestination) },
        onDocumentClicked = {}
      )
    }

    composable<AddPromptDestination> {
      AddPromptScreen(
        onBackClicked = { navHostController.navigateUp() },
        onGenerateDocumentClicked = { fullPrompt, examplePrompt ->
          navHostController.navigate(
            DocumentDestination(
              fullPrompt = fullPrompt,
              examplePrompt = examplePrompt
            )
          )
        }
      )
    }

    composable<DocumentDestination> {
      val route = it.toRoute<DocumentDestination>()
      DocumentScreen(
        fullPrompt = route.fullPrompt,
        examplePrompt = route.examplePrompt,
        onBackClicked = { navHostController.navigateUp() }
      )
    }
  }
}
