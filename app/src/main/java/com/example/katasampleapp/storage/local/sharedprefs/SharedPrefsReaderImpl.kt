package com.example.katasampleapp.storage.local.sharedprefs

import android.content.SharedPreferences
import com.example.katasampleapp.storage.local.sharedprefs.contracts.SharedPrefsReader

class SharedPrefsReaderImpl(private val sharedPreferences: SharedPreferences) : SharedPrefsReader {
    override fun getDarkModeSetting(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }
}