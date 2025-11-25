package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.domain.ai.AiGenerator
import com.google.firebase.ai.type.GenerateContentResponse
import kotlinx.coroutines.flow.Flow

class FakeAiGeneratorUseCase: AiGenerator {
  override suspend fun generateDocument(
    examplePrompt: String,
    userPrompt: String
  ): Flow<GenerateContentResponse> {
    TODO("Not yet implemented")
  }
}