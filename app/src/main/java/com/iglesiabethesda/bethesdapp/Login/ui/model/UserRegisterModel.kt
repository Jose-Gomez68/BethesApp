package com.iglesiabethesda.bethesdapp.Login.ui.model

import java.util.Date

data class UserRegisterModel(
    val uid: String = "",
    val name:String,
    val apPaterno: String,
    val apMaterno: String,
    val hobby: String,
    val job: String,
    val tel: String,
    val emergencyContact: String,
    val email: String,
    val birdthDay: Date
)
