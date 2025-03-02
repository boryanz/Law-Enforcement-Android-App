package com.boryanz.upszakoni.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.boryanz.upszakoni.data.local.database.model.BonusSalaryTreshold
import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats

@Dao
interface BonusSalaryDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMonthlyStats(yearlyStats: List<MonthlyStats>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDaysInMonthsStats(dailyStats: List<DayInMonth>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTreshold(bonusSalaryTreshold: BonusSalaryTreshold)

    @Query("SELECT * FROM bonus_salary_treshold WHERE id = :id")
    suspend fun getTreshold(id: String): BonusSalaryTreshold?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonthlyStats(monthlyStats: MonthlyStats)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayInMonthStats(dayInMonth: DayInMonth)

    @Query("SELECT * FROM monthly_stats ORDER BY month_order ASC")
    suspend fun getYearlyStats(): List<MonthlyStats>

    @Query("SELECT * FROM day_in_month WHERE month = :month ORDER BY id ASC")
    suspend fun getAllDailyStatsByMonth(month: String): List<DayInMonth>

    @Query("SELECT * FROM monthly_stats WHERE month = :month")
    suspend fun getMonthlyStatsByMonth(month: String): MonthlyStats?

    @Query("DELETE FROM bonus_salary_treshold")
    fun deleteTreshold()
}