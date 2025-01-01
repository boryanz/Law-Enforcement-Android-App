package com.example.katasampleapp.errorhandling.errorhandler

import com.example.katasampleapp.errorhandling.KataError
import com.example.katasampleapp.errorhandling.KataException


class GeneralErrorHandler : ErrorHandler {

    override fun handle(exception: Exception): KataError {
       return when (exception) {
            KataException.NoConnection -> KataError.NoInternetConnection
            KataException.BadRequest -> KataError.BadRequest
            else -> KataError.Unknown
        }
    }
}