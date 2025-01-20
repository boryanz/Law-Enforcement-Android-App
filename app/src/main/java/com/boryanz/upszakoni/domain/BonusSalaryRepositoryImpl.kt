package com.boryanz.upszakoni.domain

import android.content.Context
import com.boryanz.upszakoni.data.local.database.UpsDatabase
import com.boryanz.upszakoni.data.local.database.model.BonusSalaryTreshold
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats
import com.boryanz.upszakoni.domain.errorhandling.Result
import com.boryanz.upszakoni.domain.errorhandling.UpsError
import com.boryanz.upszakoni.domain.errorhandling.tryCatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BonusSalaryRepositoryImpl(context: Context) : BonusSalaryRepository {

    private val db = UpsDatabase.getDb(context)
    private val dao = db.bonusSalaryDao()
    private val ioDispatcher = Dispatchers.IO

    override suspend fun insertTreshold(bonusSalaryTreshold: BonusSalaryTreshold): Result<UpsError, Unit> =
        tryCatch {
            withContext(ioDispatcher) {
                dao.insertTreshold(bonusSalaryTreshold)
            }
        }

    override suspend fun getTreshold(id: String): Result<UpsError, BonusSalaryTreshold?> = tryCatch {
        withContext(ioDispatcher) {
            dao.getTreshold(id)
        }
    }

    override suspend fun insertMonthlyStats(monthlyStats: MonthlyStats): Result<UpsError, Unit> = tryCatch {
        withContext(ioDispatcher) {
            dao.insertMonthlyStats(monthlyStats)
        }
    }

    override suspend fun getYearlyStatistics(): Result<UpsError, List<MonthlyStats>> = tryCatch {
        withContext(ioDispatcher) {
            dao.getYearlyStats()
        }
    }

    override suspend fun getMonthlyStats(month: String): Result<UpsError, MonthlyStats> = tryCatch {
        withContext(ioDispatcher) {
            dao.getMonthlyStatsByMonth(month)
        }
    }

}