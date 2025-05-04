package com.iglesiabethesta.bethestaapp.Login.ui

data class LoginViewState(
    val isLoading: Boolean = false,
    val isValidEmail: Boolean = true,
    val isValidPassword: Boolean = true
)