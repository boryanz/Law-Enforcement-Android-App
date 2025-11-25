package com.boryanz.upszakoni.domain.ai

import com.google.firebase.ai.type.GenerateContentResponse
import kotlinx.coroutines.flow.Flow

interface AiGenerator {

  suspend fun generateDocument(
    examplePrompt: String,
    userPrompt: String
  ): Flow<GenerateContentResponse>
}