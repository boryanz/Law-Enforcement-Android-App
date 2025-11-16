package com.boryanz.upszakoni.domain

import android.annotation.SuppressLint
import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.ui.components.defaultMonthlyStats
import java.time.Year
import java.time.YearMonth

class GenerateDaysInMonthsUseCase() {
  @SuppressLint("NewApi")
  operator fun invoke(): List<DayInMonth> {
    return defaultMonthlyStats.map { month ->
      val daysInMonths: List<Int> = (1..getDaysForMonth(month.monthOrder)).toList()
      daysInMonths.map { monthOrder ->
        DayInMonth(
          dayNumber = monthOrder,
          month = month.month,
          isSickDay = false,
          isPaidAbsentDay = false,
          overtimeHours = "0",
          additionalNote = ""
        )
      }
    }.flatten()
  }

  private fun getDaysForMonth(monthOrder: Int): Int {
    val daysInMonths = daysInEachMonth()[monthOrder] ?: 0
    return daysInMonths
  }

  private fun daysInEachMonth(): Map<Int, Int> =
    Year.of(Year.now().value).daysInEachMonth()

  private fun Year.daysInEachMonth(): Map<Int, Int> =
    (1..12).associateWith { month ->
      YearMonth.of(value, month).lengthOfMonth()
    }

}