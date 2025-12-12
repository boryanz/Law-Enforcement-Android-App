package com.boryanz.upszakoni.domain.offenses

import com.boryanz.upszakoni.domain.Result
import com.boryanz.upszakoni.domain.offenses.model.Offense

interface OffensesProvider {

  suspend fun getOffensesByType(type: String): Result<List<Offense>>
}