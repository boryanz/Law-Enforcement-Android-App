package com.boryanz.upszakoni.domain.offenses

import com.boryanz.upszakoni.domain.Result

interface OffensesProvider {

  suspend fun getOffensesByType(type: String): Result<List<Offense>>
}