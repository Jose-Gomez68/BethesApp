package com.iglesiabethesda.bethesdapp.members.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MemberDetailsViewModel @Inject constructor(
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


}