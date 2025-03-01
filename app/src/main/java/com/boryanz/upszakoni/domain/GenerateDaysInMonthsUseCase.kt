package com.boryanz.upszakoni.domain

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.ui.components.defaultMonthlyStats
import java.time.Year
import java.time.YearMonth

class GenerateDaysInMonthsUseCase(private val bonusSalaryRepository: BonusSalaryRepository) {
    @SuppressLint("NewApi")
    suspend operator fun invoke() {
        defaultMonthlyStats.map { month ->
            val daysInMonths: List<Int> = (0 until getDaysForMonth(month.monthOrder)).toList()
            val dailyEntries = daysInMonths.map {
                DayInMonth(
                    month = month.month,
                    isSickDay = false,
                    isPaidAbsentDay = false,
                    overtimeHours = ""
                )
            }
            bonusSalaryRepository.insertDayInMonthStats(dailyEntries)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDaysForMonth(monthOrder: Int): Int {
        val daysInMonths = daysInEachMonth()[monthOrder] ?: 0
        return daysInMonths
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun daysInEachMonth(): Map<Int, Int> =
        Year.of(Year.now().value).daysInEachMonth()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun Year.daysInEachMonth(): Map<Int, Int> =
        (1..12).associateWith { month ->
            YearMonth.of(value, month).lengthOfMonth()
        }

}