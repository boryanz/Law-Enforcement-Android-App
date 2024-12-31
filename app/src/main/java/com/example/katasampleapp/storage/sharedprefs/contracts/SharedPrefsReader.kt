package com.example.katasampleapp.storage.sharedprefs.contracts

interface SharedPrefsReader {

    /**
     * Sample contract, change to match project needs.
     */
    fun getDarkModeSetting(key: String): Boolean
}