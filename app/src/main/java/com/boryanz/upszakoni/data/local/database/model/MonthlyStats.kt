package com.boryanz.upszakoni.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthly_stats")
data class MonthlyStats(
    @PrimaryKey val month: String,
    @ColumnInfo(name = "current_overtime_hours") val currentOvertimeHours: String,
    @ColumnInfo(name = "current_absence_days") val currentAbsenceDays: String,
    @ColumnInfo(name = "current_paid_absence_days") val currentPaidAbsenceDays: String,
)
