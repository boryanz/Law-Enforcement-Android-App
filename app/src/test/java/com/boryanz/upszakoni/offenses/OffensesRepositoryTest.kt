package com.boryanz.upszakoni.offenses

import com.boryanz.upszakoni.data.remote.interceptors.UpsException
import com.boryanz.upszakoni.data.remote.model.offenses.OffenseResponse
import com.boryanz.upszakoni.data.remote.service.OffensesApiService
import com.boryanz.upszakoni.domain.Result
import com.boryanz.upszakoni.domain.UpsError
import com.boryanz.upszakoni.domain.offenses.OffensesRepository
import com.boryanz.upszakoni.domain.offenses.model.Offense
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class OffensesRepositoryTest {

  private val offenseResponses = mutableListOf<OffenseResponse>(
    OffenseResponse(
      article = "чл 14 ст. 2",
      description = "Тепачка",
      fine = "100 до 400 евра",
      keywords = listOf("тепачка, насилство, бокс")
    ),
    OffenseResponse(
      article = "чл 15 ст. 3",
      description = "Тепачка",
      fine = "100 до 400 евра",
      keywords = listOf("тепачка, насилство, бокс")
    ),
    OffenseResponse(
      article = "чл 16 ст. 4",
      description = "Тепачка",
      fine = "100 до 400 евра",
      keywords = listOf("тепачка, насилство, бокс")
    ),
  )


  @Test
  fun `get offenses by type successfully from the api`() = runTest {
    val expectedOffenses = listOf(
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

    val fakeApiService = FakeOffensesApiService().apply {
      offenses = offenseResponses
    }
    val offensesRepository = OffensesRepository(fakeApiService)
    val result = offensesRepository.getOffensesByType("public-peace")
    assertEquals(Result.Success(expectedOffenses), result)
  }

  @Test
  fun `get offenses by type fails with no network exception`() = runTest {
    val fakeApiService = FakeOffensesApiService().apply { noNetwork = true }
    val offensesRepository = OffensesRepository(fakeApiService)
    val result = offensesRepository.getOffensesByType("public-peace")
    assertEquals(Result.Error(UpsError.NoInternetConnectionError), result)
  }
}


class FakeOffensesApiService() : OffensesApiService {

  var offenses = mutableListOf<OffenseResponse>()
  var noNetwork: Boolean = false

  override suspend fun getOffensesByType(type: String): List<OffenseResponse> {
    return when {
      noNetwork -> throw UpsException.NoNetworkException()
      type == "public-peace" -> offenses
      else -> emptyList()
    }
  }
}