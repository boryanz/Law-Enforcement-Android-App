package com.example.katasampleapp.data.remote.restapi

import retrofit2.http.GET

interface KataRestApi {

    @GET("base-url")
    fun getKataSample(): Unit
}