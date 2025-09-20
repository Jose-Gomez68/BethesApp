package com.iglesiabethesda.bethesdapp.util

import java.text.SimpleDateFormat
import java.util.Calendar
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

    fun parseDateFlexible(dateString: String): Date? {
        if (dateString.isBlank()) return null

        val patterns = listOf(
            "dd/MM/yyyy",                          // lo que guardas con DatePicker
            "EEE MMM dd HH:mm:ss zzz yyyy"         // lo que ves en Firebase (Date.toString)
        )

        for (pattern in patterns) {
            try {
                val locale = if (pattern.contains("EEE") || pattern.contains("MMM")) {
                    Locale.ENGLISH  // para leer lo que Firebase guarda (en inglés)
                } else {
                    Locale("es", "MX") // para leer entradas en español (si las hubiera)
                }
                val sdf = SimpleDateFormat(pattern, locale)
                val parsed = sdf.parse(dateString)
                if (parsed != null) return parsed
            } catch (_: Exception) {
                // seguimos con el siguiente patrón
            }
        }
        return null
    }

    fun ageCalculated(birthDay: Date): Int {
        val nacimiento = Calendar.getInstance().apply {
            time = birthDay
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val hoy = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        var edad = hoy.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR)

        // Si todavía no cumple años este año
        if (hoy.get(Calendar.MONTH) < nacimiento.get(Calendar.MONTH) ||
            (hoy.get(Calendar.MONTH) == nacimiento.get(Calendar.MONTH) &&
                    hoy.get(Calendar.DAY_OF_MONTH) < nacimiento.get(Calendar.DAY_OF_MONTH))) {
            edad--
        }

        return if (edad >= 0) edad else 0
    }

    fun formatDateInSpanish(date: Date): String {
        val sdf = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("es", "MX"))
        return sdf.format(date)
    }

}