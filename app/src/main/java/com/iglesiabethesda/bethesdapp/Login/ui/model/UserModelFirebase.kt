package com.iglesiabethesda.bethesdapp.Login.ui.model

import java.util.Date

class UserModelFirebase{
    var uid: String = ""
    var realName: String = ""
    var nickName: String = ""
    var email: String = ""
    var statusAccount: Int = 0
    var createdDate: Date = Date()
    var updateDate: Date = Date()

    fun toModel(): UserModel {
        return UserModel(
            uid = uid,
            realName = realName,
            nickName = nickName,
            email = email,
            statusAccount = statusAccount,
            createdDate = createdDate,
            updateDate = updateDate
        )
    }
}
