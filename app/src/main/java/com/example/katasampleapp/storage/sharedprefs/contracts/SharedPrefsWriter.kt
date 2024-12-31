package com.example.katasampleapp.storage.sharedprefs.contracts

interface SharedPrefsWriter {
    suspend fun save(key: String, value: Any)
}