package com.example.katasampleapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.example.katasampleapp.data.local.sharedprefs.SharedPrefsInitializer
import com.example.katasampleapp.di.appModule
import com.example.katasampleapp.ui.notifications.NOTIFICATION_CHANNEL_ID
import org.koin.core.context.startKoin


class KataSampleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initNotificationChannel()
        initializeKoin()
        initSharedPreferences()
    }

    private fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManagerCompat.from(this).apply {
                createNotificationChannel(
                    NotificationChannel(
                        NOTIFICATION_CHANNEL_ID,
                        "kata_sample_notification_channel",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                )
            }
        }
    }

    private fun initSharedPreferences() {
        SharedPrefsInitializer.init(context = this, "kata_shared_prefs")
    }

    private fun initializeKoin() {
        startKoin {
            modules(appModule)
        }
    }
}