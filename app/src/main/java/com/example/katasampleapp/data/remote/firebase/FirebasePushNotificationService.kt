package com.example.katasampleapp.data.remote.firebase

import com.example.katasampleapp.R
import com.example.katasampleapp.data.local.sharedprefs.SharedPrefsInitializer
import com.example.katasampleapp.ui.notifications.LocalNotificationFactory
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebasePushNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        SharedPrefsInitializer.sharedPrefsWriter.save("messagingDeviceToken", token)
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