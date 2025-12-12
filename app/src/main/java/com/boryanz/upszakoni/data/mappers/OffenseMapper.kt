package com.boryanz.upszakoni.data.mappers

import com.boryanz.upszakoni.data.remote.model.offenses.OffenseResponse
import com.boryanz.upszakoni.domain.offenses.model.Offense

fun OffenseResponse.toDomain() = Offense(
  lawArticle = this.article,
  description = this.description,
  fine = this.fine.orEmpty(),
  keywords = this.keywords
)