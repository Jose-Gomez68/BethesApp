package com.iglesiabethesda.bethesdapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UtilsFunctions {

    fun parseDateFromStringBirthDay(dateString: String, pattern: String = "dd/MM/yyyy"): Date {
        return try {
            val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            dateFormat.parse(dateString) ?: Date()
        } catch (e: Exception) {
            Date() // Valor por defecto en caso de error
        }
    }

    fun getCurrentDateTime(): Date {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return format.parse(format.format(Date())) ?: Date()
    }

    fun generateMemberCode(name: String, apPaterno: String, birthDate: Date): String {
        val namePart = if (name.length >= 2) name.substring(0, 2).uppercase(Locale.getDefault()) else name.uppercase(Locale.getDefault())
        val apPart = if (apPaterno.length >= 2) apPaterno.substring(0, 2).uppercase(Locale.getDefault()) else apPaterno.uppercase(Locale.getDefault())

        val dateFormat = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
        val dateStr = dateFormat.format(birthDate)

        return "$namePart$apPart$dateStr"
    }


}