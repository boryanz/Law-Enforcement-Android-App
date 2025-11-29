package com.boryanz.upszakoni.domain.ai

import com.boryanz.upszakoni.ui.screens.ai.AI_SYSTEM_PROMPT
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.type.GenerateContentResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GenerateAiDocument(
  private val gemini2flashGenModel: GenerativeModel,
) : AiGenerator {
  override suspend fun generateDocument(
    examplePrompt: String,
    userPrompt: String
  ): Flow<GenerateContentResponse> {
    val prompt = """
        Следи го овој стил на пушување белешка: $examplePrompt
        Следи ги главните инструкции: $AI_SYSTEM_PROMPT
        Од корисникот: $userPrompt
      """.trimIndent()

    try {
      return gemini2flashGenModel.generateContentStream(prompt)
    } catch (e: Exception) {
      println("#### Exception occured: ${e.message}")
      return emptyFlow()
    }
  }
}