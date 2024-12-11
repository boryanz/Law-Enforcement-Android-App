package com.boryanz.upszakoni.data.model

data class Offense(
    val lawName: String,
    val title: String,
    val description: String,
    val pagesToLoad: List<Int>,
)
