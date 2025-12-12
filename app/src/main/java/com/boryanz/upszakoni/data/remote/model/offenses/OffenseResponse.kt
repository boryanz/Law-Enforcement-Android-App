package com.boryanz.upszakoni.data.remote.model.offenses


import com.fasterxml.jackson.annotation.JsonProperty

data class OffenseResponse(
  @param:JsonProperty("article")
  val article: String,
  @param:JsonProperty("description")
  val description: String,
  @param:JsonProperty("fine")
  val fine: String?,
  @param:JsonProperty("keywords")
  val keywords: List<String>
)