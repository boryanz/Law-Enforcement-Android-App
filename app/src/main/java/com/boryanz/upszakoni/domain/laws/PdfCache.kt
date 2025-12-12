package com.boryanz.upszakoni.domain.laws

import okhttp3.ResponseBody

interface PdfCache {

  suspend fun hasPdf(fileName: String): Boolean
  suspend fun getPdfPath(fileName: String): String?
  suspend fun saveAndGet(fileName: String, responseBody: ResponseBody): String
}

