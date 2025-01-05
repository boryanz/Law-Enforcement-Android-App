package com.example.katasampleapp.di

import com.example.katasampleapp.data.remote.KataRestApi
import com.example.katasampleapp.data.remote.KataRestClient
import org.koin.dsl.module

val appModule = module {
    // add your dependencies here
    single { get<KataRestClient>().createService(KataRestApi::class.java) }
}