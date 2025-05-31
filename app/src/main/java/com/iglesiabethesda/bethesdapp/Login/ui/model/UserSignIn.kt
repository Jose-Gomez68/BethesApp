package com.iglesiabethesda.bethesdapp.Login.ui.model

data class UserSignIn(
    val realName: String,
    val nickName: String,
    val email: String,
    val password: String,
    val passwordConfirmation: String,
    val statusAccount: Int,
) {
    fun isNotEmpty() =
        realName.isNotEmpty() && nickName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && passwordConfirmation.isNotEmpty()
}