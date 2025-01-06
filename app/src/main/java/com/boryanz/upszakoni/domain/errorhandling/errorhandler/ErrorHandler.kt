package com.boryanz.upszakoni.domain.errorhandling.errorhandler

import com.boryanz.upszakoni.domain.errorhandling.UpsError

interface ErrorHandler {
    fun handle(exception: Exception): UpsError
}