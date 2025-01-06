package com.boryanz.upszakoni.domain

import com.boryanz.upszakoni.data.local.database.model.BonusSalaryTreshold
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats
import com.boryanz.upszakoni.domain.errorhandling.UpsError
import com.boryanz.upszakoni.domain.errorhandling.Result

interface BonusSalaryRepository {
    suspend fun insertTreshold(bonusSalaryTreshold: BonusSalaryTreshold): Result<UpsError, Unit>
    suspend fun getTreshold(id: Long): Result<UpsError, BonusSalaryTreshold>
    suspend fun insertMonthlyStats(monthlyStats: MonthlyStats): Result<UpsError, Unit>
    suspend fun getYearlyStatistics(): Result<UpsError, List<MonthlyStats>>
    suspend fun getMonthlyStats(month: String): Result<UpsError, MonthlyStats>
}