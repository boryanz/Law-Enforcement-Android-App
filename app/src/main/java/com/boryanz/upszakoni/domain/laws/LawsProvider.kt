package com.boryanz.upszakoni.domain.laws

import com.boryanz.upszakoni.data.remote.model.laws.Law
import okhttp3.ResponseBody

interface LawsProvider {
  suspend fun getLaws(): List<Law>
  suspend fun getLawById(id: String): ResponseBody
}