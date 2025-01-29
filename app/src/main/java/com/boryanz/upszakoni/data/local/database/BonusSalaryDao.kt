package com.boryanz.upszakoni.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.boryanz.upszakoni.data.local.database.model.BonusSalaryTreshold
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats

@Dao
interface BonusSalaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(yearlyStats: List<MonthlyStats>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTreshold(bonusSalaryTreshold: BonusSalaryTreshold): Long

    @Query("SELECT * FROM bonus_salary_treshold WHERE id = :id")
    suspend fun getTreshold(id: String): BonusSalaryTreshold?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonthlyStats(monthlyStats: MonthlyStats)

    @Query("SELECT * FROM monthly_stats ORDER BY month_order ASC")
    suspend fun getYearlyStats(): List<MonthlyStats>

    @Query("SELECT * FROM monthly_stats WHERE month = :month")
    suspend fun getMonthlyStatsByMonth(month: String): MonthlyStats?
}