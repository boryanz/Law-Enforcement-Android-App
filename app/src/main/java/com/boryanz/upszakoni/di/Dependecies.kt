package com.boryanz.upszakoni.di

import org.koin.dsl.module

val appModule = module {
  singles()
  useCases()
  viewModels()
}