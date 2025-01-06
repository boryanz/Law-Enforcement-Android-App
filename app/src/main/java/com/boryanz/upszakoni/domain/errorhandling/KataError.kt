package com.boryanz.upszakoni.domain.errorhandling

sealed interface KataError {
    data object BadRequest : KataError
    data object NoInternetConnection : KataError
    data object Unknown : KataError
}