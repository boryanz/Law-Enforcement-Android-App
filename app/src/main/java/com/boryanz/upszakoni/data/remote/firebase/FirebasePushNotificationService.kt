package com.boryanz.upszakoni.data.remote.firebase

import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.notifications.LocalNotificationFactory
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebasePushNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        LocalNotificationFactory(
            notificationId = 123,
            title = "Firebase service title",
            description = "Description service",
            iconRes = R.drawable.ic_launcher_foreground
        ).createNotification(this)
    }
}