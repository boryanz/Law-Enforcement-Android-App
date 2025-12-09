package com.boryanz.upszakoni.domain.offenses

import com.boryanz.upszakoni.data.model.Offense
import com.boryanz.upszakoni.data.remote.service.OffensesApiService

class OffensesRepository(private val api: OffensesApiService) : OffensesProvider {

  override suspend fun getOffensesByType(type: String): List<Offense> {
    TODO("Not yet implemented")
  }
}