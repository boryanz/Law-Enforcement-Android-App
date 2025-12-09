package com.boryanz.upszakoni.data.remote

import retrofit2.http.GET

interface LawApiService {
  @GET("api/laws")
  suspend fun getLaws(): List<Law>
}