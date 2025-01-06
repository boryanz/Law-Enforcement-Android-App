package com.boryanz.upszakoni.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bonus_salary_treshold")
data class BonusSalaryTreshold(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "minimum_overtime_hours") val minimumOvertimeHours: String,
    @ColumnInfo(name = "maximum_absence_days") val maximumAbsenceDays: String,
    @ColumnInfo(name = "maximum_paid_absence_days") val maximumPaidAbsenceDays: String,
)
