package com.boryanz.upszakoni.data.remote.service

import com.boryanz.upszakoni.data.remote.model.Law
import retrofit2.http.GET

interface LawApiService {
  @GET("api/laws")
  suspend fun getLaws(): List<Law>
}