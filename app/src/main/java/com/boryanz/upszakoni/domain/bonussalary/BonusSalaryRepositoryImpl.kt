package com.boryanz.upszakoni.domain.bonussalary

import android.content.Context
import com.boryanz.upszakoni.data.local.database.UpsDatabase
import com.boryanz.upszakoni.data.local.database.model.BonusSalaryTreshold
import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats
import com.boryanz.upszakoni.domain.GenerateDaysInMonthsUseCase
import com.boryanz.upszakoni.ui.components.defaultMonthlyStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.ceil

const val NUMBER_OF_MONTHS = 12

class BonusSalaryRepositoryImpl(
  context: Context
) : BonusSalaryRepository, KoinComponent {

  private val db = UpsDatabase.getInstance(context)
  private val dao = db.bonusSalaryDao()
  private val ioDispatcher = Dispatchers.IO

  private val generateDefaultDaysInMonthsUseCase: GenerateDaysInMonthsUseCase by inject()

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

  override suspend fun deleteAllAndGenerateDefaultData() {
    runCatching {
      withContext(ioDispatcher) {
        dao.deleteAllDaysInMonths()
        dao.deleteTreshold()
        dao.insertAllMonthlyStats(defaultMonthlyStats)
        generateDefaultDaysInMonthsUseCase()
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