package com.boryanz.upszakoni.utils

fun String.removePdfExtension() = replace(".pdf", "").trim()

fun String.mapToOrder() = when (this) {
    "Јануари" -> 1
    "Февруари" -> 2
    "Март" -> 3
    "Април" -> 4
    "Мај" -> 5
    "Јуни" -> 6
    "Јули" -> 7
    "Август" -> 8
    "Септември" -> 9
    "Октомври" -> 10
    "Ноември" -> 11
    "Декември" -> 12
    else -> throw IllegalArgumentException()
}