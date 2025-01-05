package com.example.katasampleapp.data.local.sharedprefs.contracts

interface SharedPrefsWriter {
    suspend fun save(key: String, value: Any)
}