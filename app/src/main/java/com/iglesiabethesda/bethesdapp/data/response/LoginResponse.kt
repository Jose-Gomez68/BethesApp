package com.iglesiabethesda.bethesdapp.data.response

sealed class LoginResult {

    object Error: LoginResult()
    object NetworkError : LoginResult() // Sin internet u otro fallo de red
    data class Success(val verified: Boolean): LoginResult()

}