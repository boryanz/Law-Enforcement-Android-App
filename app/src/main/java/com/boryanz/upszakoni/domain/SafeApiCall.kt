package com.boryanz.upszakoni.domain

import com.boryanz.upszakoni.data.remote.interceptors.UpsException.GeneralException
import com.boryanz.upszakoni.data.remote.interceptors.UpsException.InternalServerErrorException
import com.boryanz.upszakoni.data.remote.interceptors.UpsException.NoNetworkException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

suspend inline fun <S> safeApi(crossinline block: suspend () -> S): Result<S> = try {
  withContext(Dispatchers.IO) {
    withTimeout(5000) {
      Result.Success(block())
    }
  }
} catch (e: Exception) {
  when (e) {
    is NoNetworkException -> Result.Error(error = UpsError.NoInternetConnectionError)
    is GeneralException -> Result.Error(error = UpsError.GeneralError)
    is InternalServerErrorException -> Result.Error(error = UpsError.GeneralError)
    else -> Result.Error(UpsError.GeneralError)
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
