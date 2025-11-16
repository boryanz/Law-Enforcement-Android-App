package com.boryanz.upszakoni.analytics

interface AnalyticsLogger {
  fun logScreenEntry(screenName: String)
  fun logButtonClick(buttonName: String, screenName: String)
  fun logLawClick(lawName: String)
}
