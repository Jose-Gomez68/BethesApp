package com.iglesiabethesda.bethesdapp.Login.ui.model

import java.util.Date

data class UserSignIn(
    val realName: String,
    val nickName: String,
    val email: String,
    val password: String,
    val passwordConfirmation: String,
    val statusAccount: Int,
    val createdDate: Date,
    val updateDate: Date
) {
    fun isNotEmpty() =
        realName.isNotEmpty() && nickName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && passwordConfirmation.isNotEmpty()
}