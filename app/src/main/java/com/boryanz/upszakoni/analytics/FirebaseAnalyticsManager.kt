package com.boryanz.upszakoni.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent


object FirebaseAnalyticsManager {

  private const val SCREEN_NAME = "screen_name"
  private const val BUTTON_NAME = "button_name"
  private const val BUTTON_CLICK = "button_click"

  private const val LAW_CLICK = "law_click"
  private const val LAW_NAME = "law_click"

  private lateinit var firebaseAnalytics: FirebaseAnalytics

  fun init(context: Context) {
    firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    firebaseAnalytics.setAnalyticsCollectionEnabled(true)
  }

  fun logScreenEntry(screenName: String) {
    firebaseAnalytics.logEvent(FirebaseAnalytics.Param.SCREEN_NAME) {
      param(SCREEN_NAME, screenName)
    }
  }

  fun logButtonClick(buttonName: String, screenName: String) {
    firebaseAnalytics.logEvent(BUTTON_CLICK) {
      param(BUTTON_NAME, buttonName)
      param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
    }
  }

  fun logLawClick(lawName: String) {
    firebaseAnalytics.logEvent(LAW_CLICK) {
      param(LAW_NAME, lawName)
    }
  }
}