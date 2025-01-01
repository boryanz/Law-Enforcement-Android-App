package com.example.katasampleapp.storage.local.sharedprefs

import android.content.SharedPreferences
import com.example.katasampleapp.storage.local.sharedprefs.contracts.SharedPrefsWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharedPrefsWriterImpl(private val sharedPrefs: SharedPreferences) : SharedPrefsWriter {

    private val backgroundDispatcher = Dispatchers.IO

    override suspend fun save(key: String, value: Any) = withContext(backgroundDispatcher) {
        with(sharedPrefs) {
            when (value) {
                is Int -> edit().putInt(key, value).apply()
                is String -> edit().putString(key, value).apply()
                is Boolean -> edit().putBoolean(key, value).apply()
                is Long -> edit().putLong(key, value).apply()
                is Float -> edit().putFloat(key, value).apply()
            }
        }
    }
}