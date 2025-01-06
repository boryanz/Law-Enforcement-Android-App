package com.boryanz.upszakoni.domain.errorhandling.errorhandler

import com.boryanz.upszakoni.domain.errorhandling.KataError

interface ErrorHandler {
    fun handle(exception: Exception): KataError
}