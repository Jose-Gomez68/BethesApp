package com.iglesiabethesda.bethesdapp.Login

data class CreateUserAccountModel(
    val memberCode: String,
    val email: String,
    val password: String,
    val repeatPassword: String
)
