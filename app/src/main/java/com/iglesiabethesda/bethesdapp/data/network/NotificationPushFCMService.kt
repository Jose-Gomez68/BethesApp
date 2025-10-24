package com.iglesiabethesda.bethesdapp.data.network

import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.util.NotificationHelperFirebase

class NotificationPushFCMService: FirebaseMessagingService() {

    //comunicacion del messaging con firebase
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notif = NotificationHelperFirebase
        notif.showNotification(
            context = this,
            title = message.notification?.title ?: "Sin título",
            message = message.notification?.body ?: "Sin mensaje"
        )
        //showNotificationMessage(message)
        //aqui debo llamar NotificationHelperFirebase
    }

    //esta funcion no deberia quedar asi , mi clase de helper notification esta correcta ahi
    //deb ir este show not loquetengo comentado y pasarle
    private fun showNotificationMessage(message: RemoteMessage) {
        val notificationManager = getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(this, GeneretedTokenMessageFCM.NOTIFICATIONS_CHANNEL_ID)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(1, notification)
    }

}