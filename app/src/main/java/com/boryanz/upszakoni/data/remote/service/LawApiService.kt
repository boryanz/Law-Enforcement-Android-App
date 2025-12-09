package com.boryanz.upszakoni.data.remote.service

import com.boryanz.upszakoni.data.remote.model.laws.Law
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface LawApiService {
  @GET("api/laws")
  suspend fun getLaws(): List<Law>

  @GET("api/laws/{id}")
  @Streaming
  suspend fun getLawById(
    @Path("id") id: String,
  ): ResponseBody
}