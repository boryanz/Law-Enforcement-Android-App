package com.example.katasampleapp.storage.sharedprefs

import android.content.Context
import com.example.katasampleapp.storage.sharedprefs.contracts.SharedPrefsReader
import com.example.katasampleapp.storage.sharedprefs.contracts.SharedPrefsWriter

object SharedPrefsInitializer {

    private lateinit var sharedPrefsReader: SharedPrefsReader
    private lateinit var sharedPrefsWriter: SharedPrefsWriter

    fun init(context: Context, name: String) {
        val sharedPrefs = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        sharedPrefsReader = SharedPrefsReaderImpl(sharedPrefs)
        sharedPrefsWriter = SharedPrefsWriterImpl(sharedPrefs)
    }
}