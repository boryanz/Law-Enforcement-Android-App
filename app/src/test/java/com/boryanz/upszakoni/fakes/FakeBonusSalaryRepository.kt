package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.data.local.database.model.BonusSalaryTreshold
import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.ui.screens.bonussalary.parameters.BONUS_SALARY_TRESHOLD

sealed interface Treshold {
  data object HaveTreshold : Treshold
  data object NoTreshold : Treshold
  data object Error : Treshold
}

class FakeBonusSalaryRepository(
  private val treshold: Treshold = Treshold.HaveTreshold,
  private val shouldFetchMonthlyStatsFail: Boolean = false,
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
            id = BONUS_SALARY_TRESHOLD,
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
    return Result.success(monthlyStats)
  }

  override suspend fun getAllDailyStatsByMonth(month: String): Result<List<DayInMonth>> {
    TODO("Not yet implemented")
  }

  override suspend fun getDailyStatsById(id: Int): Result<DayInMonth> {
    TODO("Not yet implemented")
  }

  override suspend fun getMonthlyStats(month: String): Result<MonthlyStats?> {
    return if (shouldFetchMonthlyStatsFail.not()) {
      Result.success(monthlyStats.find { it.month == month })
    } else Result.failure(Exception())
  }

  override suspend fun deleteAllAndGenerateDefaultData() {
    TODO("Not yet implemented")
  }

  override fun getAverageHoursPerMonth(): Int = 10

  override fun getMinimumRequiredHours(): Int = 151

  override fun getMaximumPaidAbsenceDays(): Int = 21

  private val monthlyStats = listOf(
    MonthlyStats(
      month = "Јануари",
      monthOrder = 1,
      currentOvertimeHours = "10",
      currentAbsenceDays = "0",
      currentPaidAbsenceDays = "0"
    ),
    MonthlyStats(
      month = "Февруари",
      monthOrder = 2,
      currentOvertimeHours = "10",
      currentAbsenceDays = "0",
      currentPaidAbsenceDays = "0"
    ),
    MonthlyStats(
      month = "Март",
      monthOrder = 3,
      currentOvertimeHours = "10",
      currentAbsenceDays = "0",
      currentPaidAbsenceDays = "0"
    ),
    MonthlyStats(
      month = "Април",
      monthOrder = 4,
      currentOvertimeHours = "10",
      currentAbsenceDays = "0",
      currentPaidAbsenceDays = "0"
    ),
    MonthlyStats(
      month = "Мај",
      monthOrder = 5,
      currentOvertimeHours = "10",
      currentAbsenceDays = "0",
      currentPaidAbsenceDays = "0"
    ),
    MonthlyStats(
      month = "Јуни",
      monthOrder = 6,
      currentOvertimeHours = "10",
      currentAbsenceDays = "0",
      currentPaidAbsenceDays = "0"
    ),
    MonthlyStats(
      month = "Јули",
      monthOrder = 7,
      currentOvertimeHours = "10",
      currentAbsenceDays = "0",
      currentPaidAbsenceDays = "0"
    ),
    MonthlyStats(
      month = "Август",
      monthOrder = 8,
      currentOvertimeHours = "10",
      currentAbsenceDays = "0",
      currentPaidAbsenceDays = "0"
    ),
    MonthlyStats(
      month = "Септември",
      monthOrder = 9,
      currentOvertimeHours = "10",
      currentAbsenceDays = "0",
      currentPaidAbsenceDays = "0"
    ),
    MonthlyStats(
      month = "Октомври",
      monthOrder = 10,
      currentOvertimeHours = "10",
      currentAbsenceDays = "0",
      currentPaidAbsenceDays = "0"
    ),
    MonthlyStats(
      month = "Ноември",
      monthOrder = 11,
      currentOvertimeHours = "10",
      currentAbsenceDays = "0",
      currentPaidAbsenceDays = "0"
    ),
    MonthlyStats(
      month = "Декември",
      monthOrder = 12,
      currentOvertimeHours = "10",
      currentAbsenceDays = "0",
      currentPaidAbsenceDays = "0"
    )
  )
}