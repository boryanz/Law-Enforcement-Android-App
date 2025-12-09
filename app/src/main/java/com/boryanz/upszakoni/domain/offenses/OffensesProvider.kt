package com.boryanz.upszakoni.domain.offenses

import com.boryanz.upszakoni.data.model.Offense

interface OffensesProvider {

  suspend fun getOffensesByType(type: String): List<Offense>
}