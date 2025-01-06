package com.boryanz.upszakoni

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.boryanz.upszakoni.di.appModule
import com.boryanz.upszakoni.ui.notifications.NOTIFICATION_CHANNEL_ID
import org.koin.core.context.GlobalContext.startKoin


class UpsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initNotificationChannel()
        initializeKoin()
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

    private fun initializeKoin() {
        startKoin {
            modules(appModule)
        }
    }
}