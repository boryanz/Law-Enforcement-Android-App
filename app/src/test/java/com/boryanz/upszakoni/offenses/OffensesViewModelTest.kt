package com.boryanz.upszakoni.offenses

import com.boryanz.upszakoni.domain.Result
import com.boryanz.upszakoni.domain.UpsError
import com.boryanz.upszakoni.domain.offenses.OffensesProvider
import com.boryanz.upszakoni.domain.offenses.model.Offense
import com.boryanz.upszakoni.laws.collectFlow
import com.boryanz.upszakoni.ui.screens.common.OffenseUiEvent
import com.boryanz.upszakoni.ui.screens.common.OffenseViewModel
import com.boryanz.upszakoni.ui.screens.common.OffensesUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OffensesViewModelTest {

  val expectedOffenses = mutableListOf(
    Offense(
      lawArticle = "чл 14 ст. 2",
      description = "Тепачка",
      fine = "100 до 400 евра",
      keywords = listOf("тепачка, насилство, бокс")
    ),
    Offense(
      lawArticle = "чл 15 ст. 3",
      description = "Тепачка",
      fine = "100 до 400 евра",
      keywords = listOf("тепачка, насилство, бокс")
    ),
    Offense(
      lawArticle = "чл 16 ст. 4",
      description = "Тепачка",
      fine = "100 до 400 евра",
      keywords = listOf("тепачка, насилство, бокс")
    ),
  )

  @Test
  fun `get offenses successfully`() = runTest {
    val intialExpectedState = OffensesUiState(isLoading = true, offenses = emptyList())
    val expectedUistate = OffensesUiState(
      isLoading = false,
      offenses = expectedOffenses
    )
    val fakeRepo = FakeOffensesRepository().apply { offenses = expectedOffenses }
    //When
    val viewModel = OffenseViewModel(fakeRepo)
    viewModel.getOffenses("traffic")

    assertEquals(intialExpectedState, viewModel.uiState.value)
    advanceUntilIdle()

    //Then
    assertEquals(expectedUistate, viewModel.uiState.value)
  }

  @Test
  fun `get offenses fails with network exception`() = runTest {
    Dispatchers.setMain(Dispatchers.Unconfined)
    val intialExpectedState = OffensesUiState(isLoading = false, offenses = emptyList())
    val fakeRepo = FakeOffensesRepository().apply { noNetwork = true }
    //When
    val viewModel = OffenseViewModel(fakeRepo)

    val result = collectFlow(viewModel.uiEvent) {
      viewModel.getOffenses("traffic")
      assertEquals(intialExpectedState, viewModel.uiState.value)
    }

    assertEquals(OffenseUiEvent.Failure(UpsError.NoInternetConnectionError), result.first())
  }
}

class FakeOffensesRepository : OffensesProvider {

  var noNetwork: Boolean = false
  var offenses = mutableListOf<Offense>()

  override suspend fun getOffensesByType(type: String): Result<List<Offense>> {
    return when {
      noNetwork -> Result.Error(UpsError.NoInternetConnectionError)
      offenses.isNotEmpty() -> Result.Success(offenses)
      else -> TODO("Not implemented")
    }
  }

}
