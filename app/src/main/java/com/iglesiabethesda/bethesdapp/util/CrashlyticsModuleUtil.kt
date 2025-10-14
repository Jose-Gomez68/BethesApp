package com.iglesiabethesda.bethesdapp.util

import com.google.firebase.crashlytics.FirebaseCrashlytics

object CrashlyticsModuleUtil {

    fun crashLytics(exception: Exception, message: String? = null) {
        val crashlytics = FirebaseCrashlytics.getInstance()

        // Agregar un log opcional
        message?.let {
            crashlytics.log(it)
        }

        // Registrar la excepción
        crashlytics.recordException(exception)
    }
}
