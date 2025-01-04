package com.example.katasampleapp.remote

import retrofit2.Converter

data class KataRestConfiguration(
    val baseUrl: String = "", //add base url here
    val converterFactory: Converter.Factory
)
