package com.boryanz.upszakoni.domain

import com.boryanz.upszakoni.data.remote.interceptors.UpsException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

suspend fun <S> safeApi(block: suspend () -> S): Result<S> = try {
  withContext(Dispatchers.IO) {
    withTimeout(5000) {
      Result.Success(block())
    }
  }
} catch (e: Exception) {
  println("SafeApi caught exception: ${e::class.simpleName} - ${e.message}")
  e.printStackTrace()

  when (e) {
    is UpsException.NoNetworkException -> Result.Error(UpsError.NoInternetConnectionError)
    is UpsException.NotFoundException -> Result.Error(UpsError.GeneralError)
    is UpsException.InternalServerErrorException -> Result.Error(UpsError.GeneralError)
    is UpsException.GeneralException -> Result.Error(UpsError.GeneralError)

    // Fallback for any other exception
    else -> {
      println("Unexpected exception type: ${e::class.java.name}")
      Result.Error(UpsError.GeneralError)
    }
  }
}

inline fun <T, R> Result<T>.fold(
  onSuccess: (T) -> R,
  onFailure: (BaseError) -> R
): R {
  return when (this) {
    is Result.Success -> onSuccess(data)
    is Result.Error -> onFailure(error)
  }
}
