package com.example.katasampleapp.domain.errorhandling.errorhandler

import com.example.katasampleapp.domain.errorhandling.KataError

interface ErrorHandler {
    fun handle(exception: Exception): KataError
}