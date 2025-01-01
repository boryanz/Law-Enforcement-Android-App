package com.example.katasampleapp.errorhandling.errorhandler

import com.example.katasampleapp.errorhandling.KataError

interface ErrorHandler {
    fun handle(exception: Exception): KataError
}