package com.boryanz.upszakoni

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardContent
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiEvent
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiState
import org.junit.Rule
import org.junit.Test

class BonusSalaryDashboardScreenTest {

  @get:Rule
  val composeTestRule = createAndroidComposeRule<ComponentActivity>()

  private fun setupScreen() {
    var uiState by mutableStateOf(BonusSalaryDashboardUiState())
    var counter by mutableIntStateOf(3)

    composeTestRule.setContent {
      BonusSalaryDashboardContent(
        uiState = uiState,
        onUiEvent = { event ->
          when (event) {
            BonusSalaryDashboardUiEvent.DeleteAllActionButtonClicked -> {
              uiState = uiState.copy(
                deleteAllState = BonusSalaryDashboardUiState.DeleteAllState(buttonClickCounter = counter)
              )
            }

            BonusSalaryDashboardUiEvent.DeleteButtonClicked -> {
              counter--
              uiState = uiState.copy(
                deleteAllState = BonusSalaryDashboardUiState.DeleteAllState(buttonClickCounter = counter)
              )
            }

            else -> {}
          }
        },
        onMonthClicked = {},
        onBackClicked = {},
        onEditClicked = {},
        onNonWorkingDaysClicked = {},
      )
    }
  }

  @Test
  fun clickDeleteActionButton_showsDeleteConfirmation() {
    val deleteAllAction = "deleteAllAction"
    val deleteAllButton = "deleteAllButton"
    val resetStringTitle =
      composeTestRule.activity.getString(R.string.bonus_salary_reset_hours_title)
    val resetStringWarning = composeTestRule.activity.getString(R.string.bonus_salary_reset_warning)

    setupScreen()

    // Initially, delete confirmation should NOT be visible
    composeTestRule.onNodeWithText(resetStringTitle).assertDoesNotExist()
    composeTestRule.onNodeWithText(resetStringWarning).assertDoesNotExist()

    // When user clicks the delete action button
    composeTestRule.onNodeWithTag(deleteAllAction).performClick()
    composeTestRule.waitForIdle()

    // Then the delete confirmation should be visible with counter 3
    composeTestRule.onNodeWithText(resetStringTitle).assertExists()
    composeTestRule.onNodeWithText(resetStringWarning).assertExists()
    composeTestRule.onNodeWithTag(deleteAllButton).assertExists()
    val deleteButtonText3 =
      composeTestRule.activity.getString(R.string.bonus_salary_reset_button_format, 3)
    composeTestRule.onNodeWithText(deleteButtonText3).assertExists()
  }

  @Test
  fun clickDeleteButton_decrementsCounter() {
    val deleteAllAction = "deleteAllAction"
    val deleteAllButton = "deleteAllButton"

    setupScreen()

    // Setup: Show delete confirmation first
    composeTestRule.onNodeWithTag(deleteAllAction).performClick()
    composeTestRule.waitForIdle()

    val deleteButtonText3 =
      composeTestRule.activity.getString(R.string.bonus_salary_reset_button_format, 3)
    composeTestRule.onNodeWithText(deleteButtonText3).assertExists()

    // When user clicks the delete button
    composeTestRule.onNodeWithTag(deleteAllButton).performClick()
    composeTestRule.waitForIdle()

    // Then the counter should decrement to 2
    val deleteButtonText2 =
      composeTestRule.activity.getString(R.string.bonus_salary_reset_button_format, 2)
    composeTestRule.onNodeWithText(deleteButtonText2).assertExists()
    composeTestRule.onNodeWithText(deleteButtonText3).assertDoesNotExist()
  }

  @Test
  fun loadingState_showsLoaderThenContent() {
    var uiState by mutableStateOf(BonusSalaryDashboardUiState(isLoading = true))

    composeTestRule.setContent {
      BonusSalaryDashboardContent(
        uiState = uiState,
        onUiEvent = {},
        onMonthClicked = {},
        onBackClicked = {},
        onEditClicked = {},
        onNonWorkingDaysClicked = {},
      )
    }

    composeTestRule.onNodeWithTag("loader").assertExists()
    val yearlyStatsTitle = composeTestRule.activity.getString(R.string.bonus_salary_yearly_stats)
    composeTestRule.onNodeWithText(yearlyStatsTitle).assertDoesNotExist()

    // When data is fetched and loading completes
    val loadedUiState = BonusSalaryDashboardUiState(isLoading = false)
    uiState = loadedUiState
    composeTestRule.waitForIdle()

    //Then visible after setting loading to false
    composeTestRule.onNodeWithText(yearlyStatsTitle).assertExists()
  }

}