package com.boryanz.upszakoni.domain.bonussalary

import com.boryanz.upszakoni.data.local.database.BonusSalaryDao
import com.boryanz.upszakoni.data.local.database.model.bonussalary.BonusSalaryTreshold
import com.boryanz.upszakoni.data.local.database.model.bonussalary.DayInMonth
import com.boryanz.upszakoni.data.local.database.model.bonussalary.MonthlyStats
import com.boryanz.upszakoni.ui.components.defaultMonthlyStats
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.ceil

const val NUMBER_OF_MONTHS = 12

class BonusSalaryRepositoryImpl(
  private val dao: BonusSalaryDao,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BonusSalaryRepository {

  /**
   * Calculated value after setting the treshold hours.
   */
  private var averageOvertimeHours: Int = -1

  /**
   * Cached value on insert
   */
  private var minimumOvertimeHours: Int = -1

  /**
   * Cached value on insert
   */
  private var maximumAvailablePaidDays: Int = -1

  override suspend fun insertTreshold(bonusSalaryTreshold: BonusSalaryTreshold): Result<Unit> =
    runCatching {
      withContext(ioDispatcher) {
        dao.insertTreshold(bonusSalaryTreshold)
      }.also { cacheTresholdValues(bonusSalaryTreshold) }
    }

  override suspend fun getTreshold(id: String): Result<BonusSalaryTreshold?> =
    runCatching {
      withContext(ioDispatcher) {
        dao.getTreshold(id).also { treshold ->
          treshold?.let {
            cacheTresholdValues(it)
          }
        }
      }
    }

  override suspend fun insertMonthlyStats(monthlyStats: MonthlyStats): Result<Unit> =
    runCatching {
      withContext(ioDispatcher) {
        dao.insertMonthlyStats(monthlyStats)
      }
    }

  override suspend fun insertAllDayInMonthStats(daysInMonths: List<DayInMonth>): Result<Unit> =
    runCatching {
      withContext(ioDispatcher) {
        dao.insertAllDaysInMonthsStats(daysInMonths)
      }
    }

  override suspend fun insertDayStats(dayInMonth: DayInMonth): Result<Unit> = runCatching {
    withContext(ioDispatcher) {
      dao.insertDayInMonthStats(dayInMonth)
    }
  }

  override suspend fun getYearlyStatistics(): Result<List<MonthlyStats>> = runCatching {
    withContext(ioDispatcher) {
      dao.getYearlyStats()
    }
  }

  override suspend fun getAllDailyStatsByMonth(month: String): Result<List<DayInMonth>> =
    runCatching {
      withContext(ioDispatcher) {
        dao.getAllDailyStatsByMonth(month)
      }
    }

  override suspend fun getDailyStatsById(id: Int): Result<DayInMonth> = runCatching {
    withContext(ioDispatcher) {
      dao.getDailyStatsById(id)
    }
  }

  override suspend fun getMonthlyStats(month: String): Result<MonthlyStats?> =
    runCatching {
      withContext(ioDispatcher) {
        dao.getMonthlyStatsByMonth(month)
      }
    }

  override suspend fun deleteAllAndGenerateDefaultData(defaultData: List<DayInMonth>) {
    runCatching {
      withContext(ioDispatcher) {
        dao.deleteAllDaysInMonths()
        dao.deleteTreshold()
        dao.insertAllMonthlyStats(defaultMonthlyStats)
        dao.insertAllDaysInMonthsStats(dailyStats = defaultData)
        averageOvertimeHours = -1
        minimumOvertimeHours = -1
        maximumAvailablePaidDays = -1
      }
    }
  }

  override fun getAverageHoursPerMonth() = averageOvertimeHours

  override fun getMinimumRequiredHours() = minimumOvertimeHours

  override fun getMaximumPaidAbsenceDays() = maximumAvailablePaidDays

  private fun cacheTresholdValues(bonusSalaryTreshold: BonusSalaryTreshold) {
    averageOvertimeHours =
      ceil(bonusSalaryTreshold.minimumOvertimeHours.toDouble() / NUMBER_OF_MONTHS).toInt()
    minimumOvertimeHours = bonusSalaryTreshold.minimumOvertimeHours.toInt()
    maximumAvailablePaidDays = bonusSalaryTreshold.maximumAbsenceDays.toInt()
  }
}