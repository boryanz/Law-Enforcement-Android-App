package com.boryanz.upszakoni.customtab

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

class CustomTabLauncher(
    private val showTitle: Boolean = false,
    private val setUrlBarHiddenEnabled: Boolean = false,
) {

    fun launch(context: Context, url: String) {
        val customTabIntent = CustomTabsIntent.Builder().apply {
            setShowTitle(showTitle)
            setUrlBarHidingEnabled(setUrlBarHiddenEnabled)
        }.build()

        customTabIntent.launchUrl(context, url.toUri())
    }
}