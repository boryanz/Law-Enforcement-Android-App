package com.boryanz.upszakoni.data.remote.restapi

import retrofit2.Converter
import retrofit2.Retrofit

class KataRestClient(
    private val baseUrl: String,
    private val converterFactory: Converter.Factory
) {

    private val kataRetrofit = Retrofit.Builder().apply {
        baseUrl(baseUrl)
        addConverterFactory(converterFactory)
    }.build()

    fun <T> createService(serviceClass: Class<T>): T = kataRetrofit.create(serviceClass)

}