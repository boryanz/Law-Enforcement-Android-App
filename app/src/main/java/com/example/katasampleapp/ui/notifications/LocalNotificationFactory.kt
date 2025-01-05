package com.example.katasampleapp.ui.notifications

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

const val NOTIFICATION_CHANNEL_ID = "kataNotificationChannelId"

/**
 * Factory class for crating and showing local notifications.
 */
data class LocalNotificationFactory(
    val notificationId: Int,
    val title: String?,
    val description: String,
    @DrawableRes val iconRes: Int,
    val isAutoCancelable: Boolean = false,
) {

    @SuppressLint("MissingPermission")
    fun createNotification(context: Context) {
        val notificationBuilder =
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID).apply {
                setSmallIcon(iconRes)
                if (title != null) setContentTitle(title)
                setContentText(description)
                setAutoCancel(isAutoCancelable)
                setContentIntent(getPendingIntent(context))
            }.build()

        NotificationManagerCompat.from(context).notify(notificationId, notificationBuilder)
    }

    /**
     * Start activity on tap.
     */
    private fun getPendingIntent(context: Context): PendingIntent? {
        return null
    }
}