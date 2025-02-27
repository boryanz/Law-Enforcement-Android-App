package com.boryanz.upszakoni.data.local.database.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Year
import java.time.YearMonth

@Entity(tableName = "monthly_stats")
data class MonthlyStats(
    @PrimaryKey val month: String,
    @ColumnInfo(name = "month_order") val monthOrder: Int,
    @ColumnInfo(name = "current_overtime_hours") val currentOvertimeHours: String,
    @ColumnInfo(name = "current_absence_days") val currentAbsenceDays: String,
    @ColumnInfo(name = "current_paid_absence_days") val currentPaidAbsenceDays: String,
)


@Entity(tableName = "day_in_month")
data class DayInMonth(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name = "is_sick_day")
    val isSickDay: Boolean,
    @ColumnInfo(name = "is_paid_absent_day")
    val isPaidAbsentDay: Boolean,
    @ColumnInfo(name = "overtime_hours")
    val overtimeHours: String,
    @ColumnInfo(name = "month")
    val month: String,
)

@RequiresApi(Build.VERSION_CODES.O)
fun Year.daysInEachMonth(): Map<Int, Int> =
    (1..12).associateWith { month ->
        YearMonth.of(value, month).lengthOfMonth()
    }

@RequiresApi(Build.VERSION_CODES.O)
fun Int.daysInEachMonth(): Map<Int, Int> =
    Year.of(this).daysInEachMonth()

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val currentYear = Year.now()
    val tenYearsLater = currentYear.value
    val tenYearsLaterDays = tenYearsLater.daysInEachMonth()

    println("Days in each month of ${tenYearsLater}:")
    tenYearsLaterDays.forEach { (month, days) ->
        println("Month $month: $days days")
    }
}
