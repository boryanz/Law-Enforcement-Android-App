package com.example.katasampleapp.domain

import com.example.katasampleapp.domain.errorhandling.KataError
import com.example.katasampleapp.domain.errorhandling.Result

class KataRepositoryImpl(/* rest api or DB dependency */): KataRepository {
    override fun getSomething(): Result<KataError, Unit> {
        TODO("Not yet implemented")
    }

    override fun getSomething1(): Result<KataError, Unit> {
        TODO("Not yet implemented")
    }

    override fun getSomething2(): Result<KataError, Unit> {
        TODO("Not yet implemented")
    }
}