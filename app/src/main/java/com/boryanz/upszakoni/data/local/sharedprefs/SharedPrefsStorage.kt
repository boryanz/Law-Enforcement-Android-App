package com.boryanz.upszakoni.data.local.sharedprefs

import android.content.SharedPreferences
import androidx.core.content.edit

interface SharedPrefsManager {

  fun hasUserRejectedOvertimeTrackingMigration(): Boolean

  fun acceptOvertimeTrackingMigration()

  fun hasUserMigratedToNewOvertimeTracking(): Boolean

  fun acceptPrivacyPolicy()

  fun isPrivacyPolicyAccepted(): Boolean

  fun archiveLaw(lawName: String)

  fun removeArchivedLaw(lawName: String)

  fun contains(lawName: String): Boolean

  fun getAiGenerationsUsedToday(): Int

  fun incrementAiGenerationCounter()

  fun resetAiGenerationCounter()

  fun setAiGenerationCounterDate(date: String)

  fun getAiGenerationCounterDate(): String?
}

const val OVERTIME_MIGRATION_ACCEPTED = "overtimeMigrationAccept"
const val OVERTIME_MIGRATION_REJECTED = "overtimeMigrationReject"
const val PRIVACY_POLICY_ACCEPTED = "privacyPolicyAcceptance"
const val ARCHIVE_LAW_KEY = "archive"
const val AI_GENERATIONS_USED_KEY = "ai_generations_used"
const val AI_GENERATIONS_DATE_KEY = "ai_generations_date"

class PrefsLocalStorage(
  private val sharedPrefs: SharedPreferences
) : SharedPrefsManager {

  override fun hasUserRejectedOvertimeTrackingMigration(): Boolean {
    return sharedPrefs.getBoolean(OVERTIME_MIGRATION_REJECTED, false)
  }

  override fun acceptOvertimeTrackingMigration() {
    sharedPrefs.edit { putBoolean(OVERTIME_MIGRATION_ACCEPTED, true) }
  }

  override fun hasUserMigratedToNewOvertimeTracking(): Boolean {
    return sharedPrefs.getBoolean(OVERTIME_MIGRATION_ACCEPTED, false)
  }

  override fun acceptPrivacyPolicy() {
    sharedPrefs.edit { putBoolean(PRIVACY_POLICY_ACCEPTED, true) }
  }

  override fun isPrivacyPolicyAccepted(): Boolean {
    return sharedPrefs.getBoolean(PRIVACY_POLICY_ACCEPTED, false)
  }

  override fun archiveLaw(lawName: String) {
    sharedPrefs.edit { putString("$ARCHIVE_LAW_KEY/$lawName", lawName) }
  }

  override fun removeArchivedLaw(lawName: String) {
    sharedPrefs.edit { remove("$ARCHIVE_LAW_KEY/$lawName") }
  }

  override fun contains(lawName: String): Boolean {
    return sharedPrefs.contains("$ARCHIVE_LAW_KEY/$lawName")
  }

  override fun getAiGenerationsUsedToday(): Int {
    return sharedPrefs.getInt(AI_GENERATIONS_USED_KEY, 0)
  }

  override fun incrementAiGenerationCounter() {
    val currentCount = getAiGenerationsUsedToday()
    sharedPrefs.edit { putInt(AI_GENERATIONS_USED_KEY, currentCount + 1) }
  }

  override fun resetAiGenerationCounter() {
    sharedPrefs.edit { putInt(AI_GENERATIONS_USED_KEY, 0) }
  }

  override fun setAiGenerationCounterDate(date: String) {
    sharedPrefs.edit { putString(AI_GENERATIONS_DATE_KEY, date) }
  }

  override fun getAiGenerationCounterDate(): String? {
    return sharedPrefs.getString(AI_GENERATIONS_DATE_KEY, null)
  }
}

