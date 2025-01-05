package com.example.katasampleapp.domain

import com.example.katasampleapp.domain.errorhandling.KataError
import com.example.katasampleapp.domain.errorhandling.Result

interface KataRepository {
    fun getSomething(): Result<KataError, Unit>
    fun getSomething1(): Result<KataError, Unit>
    fun getSomething2(): Result<KataError, Unit>
}