package com.iglesiabethesda.bethesdapp.Login.ui.model

import java.util.Date

data class UserModel(
    val uid: String,
    val realName: String,
    val nickName: String,
    val email: String,
    val userType: String = "USER",
    val statusAccount: Int,
    val createdDate: Date,
    val updateDate: Date
)
