package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager

class FakePrefsLocalStorage(private val data: MutableMap<String, Any>) : SharedPrefsManager {

  override fun hasUserRejectedOvertimeTrackingMigration(): Boolean {
    return data["overtimeMigrationReject"] as? Boolean ?: false
  }

  override fun acceptOvertimeTrackingMigration() {
    data["overtimeMigrationAccept"] = true
  }

  override fun hasUserMigratedToNewOvertimeTracking(): Boolean {
    return data["overtimeMigrationAccept"] as? Boolean ?: false
  }

  override fun acceptPrivacyPolicy() {
    data["privacyPolicyAcceptance"] = true
  }

  override fun isPrivacyPolicyAccepted(): Boolean {
    return data["privacyPolicyAcceptance"] as? Boolean ?: false
  }

  override fun contains(lawName: String): Boolean {
    return data.containsKey("archive/$lawName")
  }

  override fun getAiGenerationsUsedToday(): Int {
    TODO("Not yet implemented")
  }

  override fun incrementAiGenerationCounter() {
    TODO("Not yet implemented")
  }

  override fun resetAiGenerationCounter() {
    TODO("Not yet implemented")
  }

  override fun setAiGenerationCounterDate(date: String) {
    TODO("Not yet implemented")
  }

  override fun getAiGenerationCounterDate(): String? {
    TODO("Not yet implemented")
  }
}
