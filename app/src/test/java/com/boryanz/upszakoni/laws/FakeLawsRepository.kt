package com.boryanz.upszakoni.laws

import com.boryanz.upszakoni.domain.Result
import com.boryanz.upszakoni.domain.UpsError
import com.boryanz.upszakoni.domain.laws.LawsProvider
import com.boryanz.upszakoni.domain.laws.model.Law

class FakeLawsRepository(private val laws: List<Law> = emptyList()) : LawsProvider {

  var failWithNoConnectionError: Boolean = false

  var downloadedFileName: String = ""

  override suspend fun getLaws(): Result<List<Law>> {
    return if (failWithNoConnectionError)
      Result.Error(UpsError.NoInternetConnectionError)
    else {
      Result.Success(laws)
    }
  }

  override suspend fun getLaw(
    fileName: String,
    id: String
  ): Result<String> {
    return if (downloadedFileName.isNotEmpty()) {
      Result.Success(downloadedFileName)
    } else {
      Result.Error(UpsError.GeneralError)
    }
  }
}
