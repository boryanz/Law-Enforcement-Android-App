package com.boryanz.upszakoni.domain.errorhandling

import java.io.IOException

sealed class UpsException : IOException() {
    data object NoConnection: UpsException()
    data object FileNotFound: UpsException()
    data object BadRequest: UpsException()
    data object UnrecoverableException: UpsException()
    data object RecoverableException: UpsException()
}