package com.boryanz.upszakoni.domain.laws

import com.boryanz.upszakoni.data.remote.service.LawApiService
import com.boryanz.upszakoni.domain.Result
import com.boryanz.upszakoni.domain.safeApi
import okhttp3.ResponseBody

class LawsRepository(private val api: LawApiService) : LawsProvider {

  private val cachedLaws: MutableList<Law> = mutableListOf()

  override suspend fun getLaws(): Result<List<Law>> {
    return if (cachedLaws.isNotEmpty()) {
      Result.Success(cachedLaws)
    } else {
      safeApi {
        api.getLaws().map { it.toDomain() }.also {
          cachedLaws.addAll(it)
        }
      }
    }
  }

  override suspend fun getLawById(id: String): ResponseBody {
    return api.getLawById(id)
  }
}