package com.boryanz.upszakoni.storage.sharedprefs

import android.content.Context

private const val privacy_policy_acceptance_key = "privacyPolicyAcceptance"

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
}