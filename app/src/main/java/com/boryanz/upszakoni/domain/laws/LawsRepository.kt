package com.boryanz.upszakoni.domain.laws

import com.boryanz.upszakoni.data.mappers.toDomain
import com.boryanz.upszakoni.data.remote.service.LawApiService
import com.boryanz.upszakoni.domain.Result
import com.boryanz.upszakoni.domain.laws.model.Law
import com.boryanz.upszakoni.domain.safeApi

class LawsRepository(
  private val api: LawApiService,
  private val pdfCache: PdfCache,
) : LawsProvider {

  private val cachedLaws: MutableList<Law> = mutableListOf()

  override suspend fun getLaws(): Result<List<Law>> {
    return if (cachedLaws.isNotEmpty()) {
      Result.Success(cachedLaws)
    } else {
      safeApi {
        api.getLaws().map { it.toDomain() }.also {
          cachedLaws.addAll(it)
        }
      }
    }
  }

  override suspend fun getLaw(fileName: String, id: String): Result<String> {
    pdfCache.getPdfPath(fileName)?.let { return Result.Success(it) }

    return safeApi {
      val responseBody = api.downloadPdf(id)
      pdfCache.saveAndGet(fileName, responseBody)
    }
  }
}