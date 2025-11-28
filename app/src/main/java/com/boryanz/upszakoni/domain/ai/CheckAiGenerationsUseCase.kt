package com.boryanz.upszakoni.domain.ai

import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CheckAiGenerationsUseCase(
  private val sharedPrefsManager: SharedPrefsManager
) : AiGenerationChecker {

  companion object {
    private const val FORMAT_ISO_8601_DATE = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val GMT_TIME_ZONE_PLUS_2 = "GMT+2"
  }

  override fun generationsUsed(): Int {
    val today = getCurrentDate()
    val lastCounterDate =
      sharedPrefsManager.getAiGenerationCounterDate()?.parseDate(FORMAT_ISO_8601_DATE)

    val isNotToday = lastCounterDate?.isToday() == false
    if (isNotToday) {
      sharedPrefsManager.setAiGenerationCounterDate(today)
      sharedPrefsManager.resetAiGenerationCounter()
    }

    return sharedPrefsManager.getAiGenerationsUsedToday()
  }

  private fun getCurrentDate(): String {
    return Date().formatDate(
      dateFormat = FORMAT_ISO_8601_DATE,
      timeZone = getGmtTimeZone()
    )
  }

  private fun Date.isToday(): Boolean {
    val today = getCurrentDate().parseDate(FORMAT_ISO_8601_DATE) ?: return false

    val todayDateOnly = today.formatDate("yyyy-MM-dd", getGmtTimeZone())
    val thisDateOnly = this.formatDate("yyyy-MM-dd", getGmtTimeZone())

    return todayDateOnly == thisDateOnly
  }

  fun getGmtTimeZone(): TimeZone {
    return TimeZone.getTimeZone(GMT_TIME_ZONE_PLUS_2)
  }

  fun String.parseDate(
    inputFormat: String = FORMAT_ISO_8601_DATE,
    timeZone: TimeZone = getGmtTimeZone(),
  ): Date? {
    try {
      val formatter =
        SimpleDateFormat(inputFormat, Locale.getDefault()).also { it.timeZone = timeZone }
      return formatter.parse(this)
    } catch (ex: Exception) {
      println("#### Failure to parse String to Date: $ex")
    }
    return null
  }

  fun Date.formatDate(dateFormat: String, timeZone: TimeZone): String {
    try {
      val format =
        SimpleDateFormat(dateFormat, Locale.getDefault())
      format.timeZone = timeZone
      return format.format(this)
    } catch (ex: Exception) {
      println("#### Failure to parse String to Date: $ex")
    }
    return ""
  }
}