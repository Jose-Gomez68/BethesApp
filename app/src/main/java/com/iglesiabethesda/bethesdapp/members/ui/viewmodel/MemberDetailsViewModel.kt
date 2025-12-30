package com.iglesiabethesda.bethesdapp.members.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.iglesiabethesda.bethesdapp.userandpermissions.domain.permissions
import com.iglesiabethesda.bethesdapp.userandpermissions.enums.Permission
import com.iglesiabethesda.bethesdapp.userandpermissions.enums.UserRole
import com.iglesiabethesda.bethesdapp.util.SharedPreferencesConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MemberDetailsViewModel @Inject constructor(
    private val shredPref: SharedPreferencesConfig
) : ViewModel()  {

    fun sendEmail(context: Context, email: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }

        context.startActivity(Intent.createChooser(intent, "Enviar correo"))
    }

    fun openWhatsApp(context: Context, phone: String) {
        val formatted = phone.replace("+", "").replace(" ", "")
        val uri = Uri.parse("https://wa.me/$formatted")

        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }

    fun callPhone(context: Context, phone: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        context.startActivity(intent)
    }

    private fun getCurrentUserRole(): UserRole {
        return runCatching {
            UserRole.valueOf(shredPref.getUserType())
        }.getOrElse {
            UserRole.USER // fallback seguro
        }
    }

    fun can(permission: Permission): Boolean {
        val role = getCurrentUserRole()
        return role.permissions().contains(permission)
    }

}