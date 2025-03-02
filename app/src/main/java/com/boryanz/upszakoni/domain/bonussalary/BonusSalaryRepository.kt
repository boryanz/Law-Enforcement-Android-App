package com.boryanz.upszakoni.domain.bonussalary

import com.boryanz.upszakoni.data.local.database.model.BonusSalaryTreshold
import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats
import com.boryanz.upszakoni.domain.errorhandling.Result
import com.boryanz.upszakoni.domain.errorhandling.UpsError

interface BonusSalaryRepository {
    suspend fun insertTreshold(bonusSalaryTreshold: BonusSalaryTreshold): Result<UpsError, Unit>
    suspend fun getTreshold(id: String): Result<UpsError, BonusSalaryTreshold?>
    suspend fun insertMonthlyStats(monthlyStats: MonthlyStats): Result<UpsError, Unit>
    suspend fun insertAllDayInMonthStats(daysInMonths: List<DayInMonth>): Result<UpsError, Unit>
    suspend fun insertDayStats(dayInMonth: DayInMonth): Result<UpsError, Unit>
    suspend fun getYearlyStatistics(): Result<UpsError, List<MonthlyStats>>
    suspend fun getDailyStatisticsByMonth(month: String): Result<UpsError, List<DayInMonth>>
    suspend fun getMonthlyStats(month: String): Result<UpsError, MonthlyStats?>
    suspend fun deleteAll()
    fun getAverageHoursPerMonth(): Int
    fun getMinimumRequiredHours(): Int
    fun getMaximumPaidAbsenceDays(): Int
}