package com.boryanz.upszakoni.domain.laws

import com.boryanz.upszakoni.domain.Result
import okhttp3.ResponseBody

interface LawsProvider {
  suspend fun getLaws(): Result<List<Law>>
  suspend fun getLawById(id: String): ResponseBody
}