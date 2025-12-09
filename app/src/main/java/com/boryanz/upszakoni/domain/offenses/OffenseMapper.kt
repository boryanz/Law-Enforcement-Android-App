package com.boryanz.upszakoni.domain.offenses

import com.boryanz.upszakoni.data.remote.model.offenses.OffenseResponse

fun OffenseResponse.toDomain() = Offense(
  lawArticle = this.article,
  description = this.description,
  fine = this.fine,
  keywords = this.keywords
)