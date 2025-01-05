package com.example.katasampleapp.domain.errorhandling

import com.example.katasampleapp.domain.errorhandling.errorhandler.ErrorHandler
import com.example.katasampleapp.domain.errorhandling.errorhandler.GeneralErrorHandler

sealed class Result<out F, out S> {
    data class Success<S>(val data: S) : Result<Nothing, S>()
    data class Failure<F>(val data: F) : Result<F, Nothing>()
}

inline fun <F, S, T> Result<F, S>.fold(onFailure: (F) -> T, onSuccess: (S) -> T): T =
    when (this) {
        is Result.Failure -> onFailure(data)
        is Result.Success -> onSuccess(data)
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