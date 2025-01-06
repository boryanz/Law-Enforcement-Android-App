package com.boryanz.upszakoni.domain.errorhandling.errorhandler

import com.boryanz.upszakoni.domain.errorhandling.KataError
import com.boryanz.upszakoni.domain.errorhandling.KataException


class GeneralErrorHandler : ErrorHandler {

    override fun handle(exception: Exception): KataError {
       return when (exception) {
            KataException.NoConnection -> KataError.NoInternetConnection
            KataException.BadRequest -> KataError.BadRequest
            else -> KataError.Unknown
        }
    }
}