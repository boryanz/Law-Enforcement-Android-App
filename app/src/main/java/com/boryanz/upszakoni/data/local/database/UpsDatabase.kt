package com.boryanz.upszakoni.data.local.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.boryanz.upszakoni.data.local.database.model.BonusSalaryTreshold
import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.data.local.database.model.MonthlyStats
import com.boryanz.upszakoni.ui.components.defaultMonthlyStats
import java.util.concurrent.Executors

@Database(
    entities = [BonusSalaryTreshold::class, MonthlyStats::class, DayInMonth::class],
    version = 2,
    exportSchema = false
)
abstract class UpsDatabase : RoomDatabase() {

    abstract fun bonusSalaryDao(): BonusSalaryDao

    companion object {
        @Volatile
        private var instance: UpsDatabase? = null


        fun getInstance(context: Context): UpsDatabase =
            instance ?: synchronized(this) {
                instance ?: getDb(context).also { instance = it }
            }


        private fun getDb(context: Context): UpsDatabase = Room.databaseBuilder(
            context = context.applicationContext,
            klass = UpsDatabase::class.java,
            name = "ups_db"
        ).addCallback(object : Callback() {
            @SuppressLint("NewApi")
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                ioThread {
                    getInstance(context).bonusSalaryDao().insertAllMonthlyStats(defaultMonthlyStats)
                }
            }
        }).build()
    }
}


private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun ioThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}