package com.example.katasampleapp.data.local.sharedprefs

import android.content.SharedPreferences
import com.example.katasampleapp.data.local.sharedprefs.contracts.SharedPrefsReader

class SharedPrefsReaderImpl(private val sharedPreferences: SharedPreferences) : SharedPrefsReader {
    override fun getDarkModeSetting(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }
}