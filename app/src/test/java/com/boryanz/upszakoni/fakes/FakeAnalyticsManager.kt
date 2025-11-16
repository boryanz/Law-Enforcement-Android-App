package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.analytics.AnalyticsLogger

class FakeAnalyticsManager : AnalyticsLogger {
  val loggedScreenEntries = mutableListOf<String>()
  val loggedButtonClicks = mutableListOf<Pair<String, String>>()
  val loggedLawClicks = mutableListOf<String>()

  override fun logScreenEntry(screenName: String) {
    loggedScreenEntries.add(screenName)
  }

  override fun logButtonClick(buttonName: String, screenName: String) {
    loggedButtonClicks.add(buttonName to screenName)
  }

  override fun logLawClick(lawName: String) {
    loggedLawClicks.add(lawName)
  }
}
