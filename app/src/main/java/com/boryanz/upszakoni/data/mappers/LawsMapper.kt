package com.boryanz.upszakoni.data.mappers

import com.boryanz.upszakoni.data.remote.model.laws.LawResponse
import com.boryanz.upszakoni.domain.laws.model.Law

fun LawResponse.toDomain() = Law(
  id = this.id,
  title = this.title
)