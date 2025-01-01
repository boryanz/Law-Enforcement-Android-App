package com.example.katasampleapp.errorhandling

import com.example.katasampleapp.errorhandling.errorhandler.ErrorHandler
import com.example.katasampleapp.errorhandling.errorhandler.GeneralErrorHandler

sealed class Result<out F, out S> {
    data class Success<S>(val data: S) : Result<Nothing, S>()
    data class Failure<F>(val data: F) : Result<F, Nothing>()
}


inline fun <reified S> tryCatch(
    errorHandler: ErrorHandler = GeneralErrorHandler(),
    block: () -> S,
): Result<KataError, S> =
    try {
        Result.Success(block())
    } catch (e: Exception) {
        Result.Failure(errorHandler.handle(e))
    }