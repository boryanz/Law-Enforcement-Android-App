package com.example.katasampleapp.storage.local.sharedprefs.contracts

interface SharedPrefsWriter {
    suspend fun save(key: String, value: Any)
}