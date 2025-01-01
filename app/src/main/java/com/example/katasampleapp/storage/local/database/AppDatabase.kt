package com.example.katasampleapp.storage.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.katasampleapp.storage.local.database.model.SampleModel

@Database(entities = [SampleModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sampleDao(): SampleDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDb(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = AppDatabase::class.java,
                    name = "app_database"
                )
                    .build()
                Companion.instance = instance
                instance
            }
        }
    }
}