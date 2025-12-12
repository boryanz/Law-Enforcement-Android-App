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
import com.boryanz.upszakoni.ui.screens.ai.history.GeneratedDocumentOverviewScreen
import com.boryanz.upszakoni.ui.screens.ai.history.GeneratedDocumentOverviewScreenDestination
import com.boryanz.upszakoni.ui.screens.ai.information.PromptInformationScreen
import kotlinx.serialization.Serializable

@Serializable
data object PromptInformationDestination

@Serializable
data object AddPromptDestination

@Serializable
data class DocumentDestination(val fullPrompt: String, val examplePrompt: String, val type: String)

@Composable
fun GenerateDocumentNavigationGraph(
  navHostController: NavHostController = rememberNavController(),
  onBackClicked: () -> Unit
) {

  NavHost(
    navController = navHostController,
    startDestination = DocumentHistoryDestination,
  ) {
    composable<DocumentHistoryDestination> {
      DocumentHistoryScreen(
        onBackClicked = onBackClicked ,
        onAddDocumentClicked = { navHostController.navigate(AddPromptDestination) },
        onDocumentClicked = {
          navHostController.navigate(
            GeneratedDocumentOverviewScreenDestination(
              it
            )
          )
        },
        onMoreInformationClicked = { navHostController.navigate(PromptInformationDestination) }
      )
    }

    composable<PromptInformationDestination> {
      PromptInformationScreen(onBackClicked = navHostController::navigateUp)
    }

    composable<GeneratedDocumentOverviewScreenDestination> {
      val route = it.toRoute<GeneratedDocumentOverviewScreenDestination>()
      GeneratedDocumentOverviewScreen(
        content = route.content,
        onBackClicked = navHostController::navigateUp,
      )
    }

    composable<AddPromptDestination> {
      AddPromptScreen(
        onBackClicked = { navHostController.navigateUp() },
        onGenerateDocumentClicked = { fullPrompt, examplePrompt, type ->
          navHostController.navigate(
            DocumentDestination(
              fullPrompt = fullPrompt,
              examplePrompt = examplePrompt,
              type = type
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
        type = route.type,
        onBackClicked = { navHostController.navigateUp() }
      )
    }
  }
}
