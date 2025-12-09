package com.boryanz.upszakoni.data.remote.interceptors

import com.boryanz.upszakoni.data.remote.interceptors.UpsErrorCodes.INTERNAL_SERVER_ERROR
import com.boryanz.upszakoni.data.remote.interceptors.UpsErrorCodes.NOT_FOUND
import com.boryanz.upszakoni.utils.ConnectionUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorInterceptor : Interceptor {

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    if (!ConnectionUtils.hasConnection()) {
      throw UpsException.NoNetworkException()
    }

    try {
      val request = chain.request()
      val response = chain.proceed(request)
      if (!response.isSuccessful) {
        when (response.code) {
          INTERNAL_SERVER_ERROR -> throw UpsException.InternalServerErrorException()
          NOT_FOUND -> throw UpsException.NotFoundException()
          else -> throw UpsException.GeneralException()
        }
      }
      return response
    } catch (e: Exception) {
      if (e is UpsException) {
        throw e
      } else {
        throw UpsException.GeneralException()
      }
    }
  }
}

sealed class UpsException : Exception() {
  class GeneralException : UpsException()
  class NoNetworkException : UpsException()
  class InternalServerErrorException : UpsException()
  class NotFoundException : UpsException()
}

data object UpsErrorCodes {
  const val NOT_FOUND = 404
  const val INTERNAL_SERVER_ERROR = 500
  const val BAD_REQUEST = 500
}