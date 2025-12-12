package com.boryanz.upszakoni.domain.laws

import com.boryanz.upszakoni.domain.Result
import com.boryanz.upszakoni.domain.laws.model.Law

interface LawsProvider {
  suspend fun getLaws(): Result<List<Law>>
  suspend fun getLaw(fileName: String, id: String): Result<String>
}