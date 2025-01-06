package com.boryanz.upszakoni.di

import com.boryanz.upszakoni.data.remote.restapi.KataRestClient
import org.koin.dsl.module

val appModule = module {
    // add your dependencies here
    single { get<KataRestClient>().createService(com.boryanz.upszakoni.data.remote.restapi.KataRestApi::class.java) }
}