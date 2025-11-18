package com.boryanz.upszakoni.data.local.database.model.bonussalary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bonus_salary_treshold")
data class BonusSalaryTreshold(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "minimum_overtime_hours") val minimumOvertimeHours: String,
    @ColumnInfo(name = "maximum_absence_days") val maximumAbsenceDays: String,
)
