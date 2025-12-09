package com.boryanz.upszakoni.di

import com.boryanz.upszakoni.domain.DaysInMonthDataGenerator
import com.boryanz.upszakoni.domain.GenerateDaysInMonthsUseCase
import com.boryanz.upszakoni.domain.GetLawsUseCase
import com.boryanz.upszakoni.domain.LawsUseCase
import com.boryanz.upszakoni.domain.ai.AiGenerationChecker
import com.boryanz.upszakoni.domain.ai.AiGenerator
import com.boryanz.upszakoni.domain.ai.CheckAiGenerationsUseCase
import com.boryanz.upszakoni.domain.ai.GenerateAiDocument
import com.boryanz.upszakoni.domain.owneditem.AddOwnedItemUseCase
import com.boryanz.upszakoni.domain.owneditem.AddOwnedItemUseCaseImpl
import com.boryanz.upszakoni.domain.owneditem.DeleteOwnedItemUseCase
import com.boryanz.upszakoni.domain.owneditem.DeleteOwnedItemUseCaseImpl
import com.boryanz.upszakoni.domain.owneditem.GetOwnedItemsUseCase
import com.boryanz.upszakoni.domain.owneditem.GetOwnedItemsUseCaseImpl
import com.google.firebase.Firebase
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module

fun Module.useCases() {
  factory<GenerativeModel> {
    Firebase.ai(backend = GenerativeBackend.googleAI()).generativeModel("gemini-2.5-flash")
  }
  factory<LawsUseCase> { GetLawsUseCase(androidContext()) }
  factory<AiGenerationChecker> { CheckAiGenerationsUseCase(get()) }
  factory<AiGenerator> { GenerateAiDocument(get()) }
  factory<GetOwnedItemsUseCase> { GetOwnedItemsUseCaseImpl(get()) }
  factory<DeleteOwnedItemUseCase> { DeleteOwnedItemUseCaseImpl(get()) }
  factory<AddOwnedItemUseCase> { AddOwnedItemUseCaseImpl(get()) }
  factory<DaysInMonthDataGenerator> { GenerateDaysInMonthsUseCase() }
}