package com.iglesiabethesda.bethesdapp.Login.ui.model

data class UserLogin (
    val email: String = "",
    val password: String = "",
    val showErrorDialog: Boolean = false
)