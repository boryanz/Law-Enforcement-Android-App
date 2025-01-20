package com.boryanz.upszakoni.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.boryanz.upszakoni.data.local.database.model.BonusSalaryTreshold
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats

@Database(entities = [BonusSalaryTreshold::class, MonthlyStats::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class UpsDatabase : RoomDatabase() {

    abstract fun bonusSalaryDao(): BonusSalaryDao

    companion object {
        @Volatile
        private var instance: UpsDatabase? = null

        fun getDb(context: Context): UpsDatabase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = UpsDatabase::class.java,
                    name = "ups_db"
                ).build()
                this.instance = instance
                instance
            }
        }
    }
}