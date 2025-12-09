package com.boryanz.upszakoni.domain.laws

import com.boryanz.upszakoni.data.remote.model.laws.LawResponse

fun LawResponse.toDomain() = Law(
  id = this.id,
  title = this.title
)