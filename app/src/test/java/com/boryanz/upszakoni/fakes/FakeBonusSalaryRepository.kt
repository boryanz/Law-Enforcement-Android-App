package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.data.local.database.model.BonusSalaryTreshold
import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository

sealed interface Treshold {
  data object HaveTreshold : Treshold
  data object NoTreshold : Treshold
  data object Error : Treshold
}

class FakeBonusSalaryRepository(
  private val treshold: Treshold
) : BonusSalaryRepository {

  override suspend fun insertTreshold(bonusSalaryTreshold: BonusSalaryTreshold): Result<Unit> {
    TODO("Not yet implemented")
  }

  override suspend fun getTreshold(id: String): Result<BonusSalaryTreshold?> {
    return when (treshold) {
      Treshold.Error, Treshold.NoTreshold -> Result.failure(Exception())
      Treshold.HaveTreshold -> {
        Result.success(
          BonusSalaryTreshold(
            id = "bonus_salary_treshold",
            minimumOvertimeHours = "150",
            maximumAbsenceDays = "21"
          )
        )
      }
    }
  }

  override suspend fun insertMonthlyStats(monthlyStats: MonthlyStats): Result<Unit> {
    TODO("Not yet implemented")
  }

  override suspend fun insertAllDayInMonthStats(daysInMonths: List<DayInMonth>): Result<Unit> {
    TODO("Not yet implemented")
  }

  override suspend fun insertDayStats(dayInMonth: DayInMonth): Result<Unit> {
    TODO("Not yet implemented")
  }

  override suspend fun getYearlyStatistics(): Result<List<MonthlyStats>> {
    TODO("Not yet implemented")
  }

  override suspend fun getAllDailyStatsByMonth(month: String): Result<List<DayInMonth>> {
    TODO("Not yet implemented")
  }

  override suspend fun getDailyStatsById(id: Int): Result<DayInMonth> {
    TODO("Not yet implemented")
  }

  override suspend fun getMonthlyStats(month: String): Result<MonthlyStats?> {
    TODO("Not yet implemented")
  }

  override suspend fun deleteAllAndGenerateDefaultData() {
    TODO("Not yet implemented")
  }

  override fun getAverageHoursPerMonth(): Int {
    TODO("Not yet implemented")
  }

  override fun getMinimumRequiredHours(): Int {
    TODO("Not yet implemented")
  }

  override fun getMaximumPaidAbsenceDays(): Int {
    TODO("Not yet implemented")
  }


}