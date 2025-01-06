package com.boryanz.upszakoni.domain.errorhandling.errorhandler

import com.boryanz.upszakoni.domain.errorhandling.UpsError
import com.boryanz.upszakoni.domain.errorhandling.UpsException

class GeneralErrorHandler : ErrorHandler {
    override fun handle(exception: Exception): UpsError {
       return when (exception) {
            UpsException.NoConnection -> UpsError.NoInternetConnection
            UpsException.BadRequest -> UpsError.BadRequest
            else -> UpsError.Unknown
        }
    }
}