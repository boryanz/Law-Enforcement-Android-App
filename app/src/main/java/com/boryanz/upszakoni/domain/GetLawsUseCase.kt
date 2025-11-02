package com.boryanz.upszakoni.domain

import android.content.Context

interface LawsUseCase {
  operator fun invoke(): List<String>
}

class GetLawsUseCase(private val context: Context) : LawsUseCase {

  override operator fun invoke(): List<String> {
    return context.assets.list("")?.mapNotNull { it }.orEmpty()
      .filter { it.contains(".pdf") }
  }
}


