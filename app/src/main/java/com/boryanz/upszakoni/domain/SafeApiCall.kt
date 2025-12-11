package com.boryanz.upszakoni.domain

import com.boryanz.upszakoni.data.remote.interceptors.UpsErrorCodes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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
    is HttpException -> {
      when (e.code()) {
        UpsErrorCodes.NOT_FOUND -> Result.Error(error = UpsError.GeneralError)
        UpsErrorCodes.INTERNAL_SERVER_ERROR -> Result.Error(error = UpsError.GeneralError)
        else -> Result.Error(error = UpsError.GeneralError)
      }
    }

    // Network-related exceptions
    is UnknownHostException -> Result.Error(error = UpsError.NoInternetConnectionError)
    is ConnectException -> Result.Error(error = UpsError.NoInternetConnectionError)
    is SocketTimeoutException -> Result.Error(error = UpsError.NoInternetConnectionError)
    is TimeoutCancellationException -> Result.Error(error = UpsError.NoInternetConnectionError)
    is IOException -> Result.Error(error = UpsError.NoInternetConnectionError)

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
