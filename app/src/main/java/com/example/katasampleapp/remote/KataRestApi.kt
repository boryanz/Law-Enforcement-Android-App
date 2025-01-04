package com.example.katasampleapp.remote

import retrofit2.http.GET

interface KataRestApi {

    @GET("base-url")
    fun getKataSample(): Unit
}