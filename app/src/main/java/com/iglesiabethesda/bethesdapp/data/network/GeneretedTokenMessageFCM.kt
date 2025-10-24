package com.iglesiabethesda.bethesdapp.data.network

import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.iglesiabethesda.bethesdapp.util.NotificationHelperFirebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class GeneretedTokenMessageFCM @Inject constructor(
    @ApplicationContext private val context: Context
) {
    //https://www.youtube.com/watch?v=qy1hqa7b_v8

    companion object {
        const val NOTIFICATIONS_CHANNEL_ID = "notification_fcm"
    }

    fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("AQUI", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "TOKEN LISTO ${token}"
            Log.d("AQUI2", msg)
            //aqui solo debo mandar mi token al servidor
            //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
        /*val notif = NotificationHelperFirebase
        notif.showNotification(
            context = context,
            title = "LA PRUEBA CHIDORIS",
            message = "MENSAJE MUY LARGO ASI QUE NO SE SI FUNCIONE "
        )*/
    }

    /*private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel()
        }
    }*/

}