package com.boryanz.upszakoni.data.remote.service

import com.boryanz.upszakoni.data.remote.model.offenses.OffenseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OffensesApiService {

  @GET("api/offenses/{type}")
  suspend fun getOffensesByType(
    @Path("type") type: String
  ): OffenseResponse
}