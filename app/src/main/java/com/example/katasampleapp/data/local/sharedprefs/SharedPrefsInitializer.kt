package com.example.katasampleapp.data.local.sharedprefs

import android.content.Context
import com.example.katasampleapp.data.local.sharedprefs.contracts.SharedPrefsReader
import com.example.katasampleapp.data.local.sharedprefs.contracts.SharedPrefsWriter

object SharedPrefsInitializer {

    lateinit var sharedPrefsReader: SharedPrefsReader
    lateinit var sharedPrefsWriter: SharedPrefsWriter

    fun init(context: Context, name: String) {
        val sharedPrefs = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        sharedPrefsReader = SharedPrefsReaderImpl(sharedPrefs)
        sharedPrefsWriter = SharedPrefsWriterImpl(sharedPrefs)
    }
}