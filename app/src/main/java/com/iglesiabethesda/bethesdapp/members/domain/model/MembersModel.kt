package com.iglesiabethesda.bethesdapp.members.domain.model

import java.util.Date

data class MembersModel(
    val uid: String = "",
    val name:String,
    val apPaterno: String,
    val apMaterno: String,
    val hobby: String,
    val job: String,
    val tel: String,
    val address: String,
    val emergencyContact: String,
    val email: String,
    val birthDay: Date,
    val statusAccount: Int,
    val createdDate: Date,
    val updateDate: Date
)