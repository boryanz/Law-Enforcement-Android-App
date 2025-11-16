package com.boryanz.upszakoni.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent


class FirebaseAnalyticsManager(
  private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsLogger {

  companion object {
    private const val SCREEN_NAME = "screen_name"
    private const val BUTTON_NAME = "button_name"
    private const val BUTTON_CLICK = "button_click"
    private const val LAW_CLICK = "law_click"
    private const val LAW_NAME = "law_click"
  }

  override fun logScreenEntry(screenName: String) {
    firebaseAnalytics.logEvent(FirebaseAnalytics.Param.SCREEN_NAME) {
      param(SCREEN_NAME, screenName)
    }
  }

  override fun logButtonClick(buttonName: String, screenName: String) {
    firebaseAnalytics.logEvent(BUTTON_CLICK) {
      param(BUTTON_NAME, buttonName)
      param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
    }
  }

  override fun logLawClick(lawName: String) {
    firebaseAnalytics.logEvent(LAW_CLICK) {
      param(LAW_NAME, lawName)
    }
  }
}