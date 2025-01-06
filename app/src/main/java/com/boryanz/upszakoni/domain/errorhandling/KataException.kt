package com.boryanz.upszakoni.domain.errorhandling

import java.io.IOException

sealed class KataException : IOException() {
    data object NoConnection: KataException()
    data object FileNotFound: KataException()
    data object BadRequest: KataException()
    data object UnrecoverableException: KataException()
    data object RecoverableException: KataException()
}