package com.boryanz.upszakoni.domain.bonussalary

import com.boryanz.upszakoni.data.local.database.model.BonusSalaryTreshold
import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats

interface BonusSalaryRepository {
  suspend fun insertTreshold(bonusSalaryTreshold: BonusSalaryTreshold): Result<Unit>
  suspend fun getTreshold(id: String): Result<BonusSalaryTreshold?>
  suspend fun insertMonthlyStats(monthlyStats: MonthlyStats): Result<Unit>
  suspend fun insertAllDayInMonthStats(daysInMonths: List<DayInMonth>): Result<Unit>
  suspend fun insertDayStats(dayInMonth: DayInMonth): Result<Unit>
  suspend fun getYearlyStatistics(): Result<List<MonthlyStats>>
  suspend fun getAllDailyStatsByMonth(month: String): Result<List<DayInMonth>>
  suspend fun getDailyStatsById(id: Int): Result<DayInMonth>
  suspend fun getMonthlyStats(month: String): Result<MonthlyStats?>
  suspend fun deleteAllAndGenerateDefaultData(defaultData: List<DayInMonth>)
  fun getAverageHoursPerMonth(): Int
  fun getMinimumRequiredHours(): Int
  fun getMaximumPaidAbsenceDays(): Int
}