package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.domain.LawsUseCase

class FakeLawsUseCase() : LawsUseCase {
  override fun invoke(): List<String> {
    return listOf("закон за прекшоци.pdf", "закон за возила.pdf", "закон за странците.pdf")
  }
}