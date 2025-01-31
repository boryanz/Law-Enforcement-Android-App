package com.boryanz.upszakoni.domain.errorhandling

sealed interface UpsError {
    data object BadRequest : UpsError
    data object NoInternetConnection : UpsError
    data object Unknown : UpsError
}