package com.iglesiabethesda.bethesdapp.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.iglesiabethesda.bethesdapp.R

object NotificationHelperFirebase {

    fun showNotification(context: Context, title: String, message: String) {

        val channelId = "notification_fcm"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //crear un canal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                channelId,
                "Eventos",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones de Eventos"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.logo) //icono que deseo
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())

    }

}