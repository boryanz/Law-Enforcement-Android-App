package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.domain.ai.AiGenerationChecker

class FakeAiCheckerUseCase(val counter: Int = 0) : AiGenerationChecker {

  override fun generationsUsed(): Int {
    return counter
  }
}