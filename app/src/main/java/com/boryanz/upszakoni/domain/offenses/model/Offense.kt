package com.boryanz.upszakoni.domain.offenses.model

data class Offense(
  val lawArticle: String,
  val description: String,
  val fine: String,
  val keywords: List<String>
) {

  val title = "$lawArticle ${"-".takeIf { fine.isNotEmpty() }.orEmpty()} $fine"
}