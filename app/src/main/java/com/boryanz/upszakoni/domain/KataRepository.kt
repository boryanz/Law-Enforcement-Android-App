package com.boryanz.upszakoni.domain

import com.boryanz.upszakoni.domain.errorhandling.KataError
import com.boryanz.upszakoni.domain.errorhandling.Result

interface KataRepository {
    fun getSomething(): Result<KataError, Unit>
    fun getSomething1(): Result<KataError, Unit>
    fun getSomething2(): Result<KataError, Unit>
}