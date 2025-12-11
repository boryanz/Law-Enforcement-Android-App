package com.boryanz.upszakoni.domain.laws

import android.content.Context
import okhttp3.ResponseBody
import java.io.File

class LocalPdfCache(private val context: Context) : PdfCache {

  override suspend fun hasPdf(fileName: String): Boolean {
    return File(context.filesDir, fileName).exists()
  }

  override suspend fun getPdfPath(fileName: String): String? {
    val file = File(context.filesDir, fileName)
    return if (hasPdf(fileName)) file.absolutePath else null
  }

  override suspend fun saveAndGet(
    fileName: String,
    responseBody: ResponseBody
  ): String {
    val file = File(context.filesDir, "$fileName.pdf")
    responseBody.byteStream().use { inputStream ->
      file.outputStream().use { fileOutputStream ->
        inputStream.copyTo(fileOutputStream)
      }
    }
    return file.absolutePath
  }
}