package com.boryanz.upszakoni.domain.laws

import com.boryanz.upszakoni.data.remote.model.laws.Law
import okhttp3.ResponseBody

class LawsRepository: LawsProvider {

  override suspend fun getLaws(): List<Law> {
    TODO("Not yet implemented")
  }

  override suspend fun getLawById(id: String): ResponseBody {
    TODO("Not yet implemented")
  }
}