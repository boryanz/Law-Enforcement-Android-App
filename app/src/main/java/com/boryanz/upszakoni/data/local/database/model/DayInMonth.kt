package com.boryanz.upszakoni.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_in_month")
data class DayInMonth(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "is_sick_day")
    val isSickDay: Boolean,
    @ColumnInfo(name = "day_number")
    val dayNumber: Int,
    @ColumnInfo(name = "is_paid_absent_day")
    val isPaidAbsentDay: Boolean,
    @ColumnInfo(name = "overtime_hours")
    val overtimeHours: String,
    @ColumnInfo(name = "month")
    val month: String,
)