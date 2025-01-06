package com.boryanz.upszakoni.data.remote.restapi

import retrofit2.Converter

data class KataRestConfiguration(
    val baseUrl: String = "", //add base url here
    val converterFactory: Converter.Factory
)
