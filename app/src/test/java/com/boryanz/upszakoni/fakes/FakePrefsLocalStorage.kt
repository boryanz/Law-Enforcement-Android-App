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

  override fun archiveLaw(lawName: String) {
    data["archive/$lawName"] = lawName
  }

  override fun removeArchivedLaw(lawName: String) {
    data.remove("archive/$lawName")
  }

  override fun contains(lawName: String): Boolean {
    return data.containsKey("archive/$lawName")
  }
}
