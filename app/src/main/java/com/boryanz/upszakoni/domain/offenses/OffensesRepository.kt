package com.boryanz.upszakoni.domain.offenses

import com.boryanz.upszakoni.data.mappers.toDomain
import com.boryanz.upszakoni.data.remote.service.OffensesApiService
import com.boryanz.upszakoni.domain.Result
import com.boryanz.upszakoni.domain.offenses.model.Offense
import com.boryanz.upszakoni.domain.safeApi

class OffensesRepository(private val api: OffensesApiService) : OffensesProvider {

  private val _cachedOffenses: MutableMap<String, List<Offense>> = mutableMapOf()

  override suspend fun getOffensesByType(type: String): Result<List<Offense>> {
    return if (!_cachedOffenses[type].isNullOrEmpty()) {
      Result.Success(_cachedOffenses[type]!!)
    } else {
      safeApi {
        api.getOffensesByType(type).map { it.toDomain() }.also { offenses ->
          _cachedOffenses[type] = offenses
        }
      }
    }
  }
}