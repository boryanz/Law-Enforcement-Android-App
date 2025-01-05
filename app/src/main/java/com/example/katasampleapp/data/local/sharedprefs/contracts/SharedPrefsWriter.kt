package com.example.katasampleapp.data.local.sharedprefs.contracts

interface SharedPrefsWriter {
    fun save(key: String, value: Any)
}