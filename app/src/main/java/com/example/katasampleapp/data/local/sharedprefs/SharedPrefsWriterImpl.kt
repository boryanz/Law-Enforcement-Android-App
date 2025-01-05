package com.example.katasampleapp.data.local.sharedprefs

import android.content.SharedPreferences
import com.example.katasampleapp.data.local.sharedprefs.contracts.SharedPrefsWriter

class SharedPrefsWriterImpl(private val sharedPrefs: SharedPreferences) : SharedPrefsWriter {

    override fun save(key: String, value: Any) {
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