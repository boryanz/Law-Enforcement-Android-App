package com.boryanz.upszakoni.data.local.sharedprefs

import android.content.Context

private const val privacy_policy_acceptance_key = "privacyPolicyAcceptance"
private const val archive_law_key = "archive"

class SharedPrefsDao(
    context: Context
) {

    private val sharedPrefs = context.getSharedPreferences("Ups shared prefs", Context.MODE_PRIVATE)

    fun acceptPrivacyPolicy() {
        sharedPrefs.edit().putBoolean(privacy_policy_acceptance_key, true).apply()
    }

    fun isPrivacyPolicyAccepted(): Boolean {
        return sharedPrefs.getBoolean(privacy_policy_acceptance_key, false)
    }

    fun archiveLaw(lawName: String) {
        sharedPrefs.edit().putString("$archive_law_key/$lawName", lawName).apply()
    }

    fun removeArchivedLaw(lawName: String) {
        sharedPrefs.edit().remove("$archive_law_key/$lawName").apply()
    }

    fun contains(lawName: String): Boolean {
        return sharedPrefs.contains("$archive_law_key/$lawName")
    }
}