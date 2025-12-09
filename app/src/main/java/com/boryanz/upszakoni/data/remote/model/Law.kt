package com.boryanz.upszakoni.data.remote.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Law(
  @param:JsonProperty("id")
  val id: String,
  @param:JsonProperty("title")
  val title: String
)