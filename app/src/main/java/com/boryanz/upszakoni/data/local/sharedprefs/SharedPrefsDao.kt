package com.boryanz.upszakoni.data.local.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

private const val overtimeTrackingMigrationAccepted = "overtimeMigrationAccept"
private const val overtimeTrackingMigrationRejected = "overtimeMigrationReject"
private const val privacy_policy_acceptance_key = "privacyPolicyAcceptance"
private const val archive_law_key = "archive"

object SharedPrefsDao {

  private lateinit var sharedPreferences: SharedPreferences

  fun init(context: Context) {
    sharedPreferences = context.getSharedPreferences("Ups shared prefs", Context.MODE_PRIVATE)
  }

  fun hasUserRejectedOvertimeTrackingMigration(): Boolean {
    return sharedPreferences.getBoolean(overtimeTrackingMigrationRejected, false)
  }

  fun acceptOvertimeTrackingMigration() {
    sharedPreferences.edit { putBoolean(overtimeTrackingMigrationAccepted, true) }
  }

  fun hasUserMigratedToNewOvertimeTracking(): Boolean {
    return sharedPreferences.getBoolean(overtimeTrackingMigrationAccepted, false)
  }

  fun acceptPrivacyPolicy() {
    sharedPreferences.edit { putBoolean(privacy_policy_acceptance_key, true) }
  }

  fun isPrivacyPolicyAccepted(): Boolean {
    return sharedPreferences.getBoolean(privacy_policy_acceptance_key, false)
  }

  fun archiveLaw(lawName: String) {
    sharedPreferences.edit { putString("$archive_law_key/$lawName", lawName) }
  }

  fun removeArchivedLaw(lawName: String) {
    sharedPreferences.edit { remove("$archive_law_key/$lawName") }
  }

  fun contains(lawName: String): Boolean {
    return sharedPreferences.contains("$archive_law_key/$lawName")
  }
}