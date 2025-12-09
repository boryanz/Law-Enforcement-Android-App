package com.boryanz.upszakoni.domain.laws

import com.boryanz.upszakoni.domain.Result
import com.boryanz.upszakoni.domain.laws.model.Law
import okhttp3.ResponseBody

interface LawsProvider {
  suspend fun getLaws(): Result<List<Law>>
  suspend fun getLawById(id: String): ResponseBody
}