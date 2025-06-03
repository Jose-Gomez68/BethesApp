package com.iglesiabethesda.bethesdapp.members.data

import java.util.Date

class MembersModelFirebase {
    var uid: String = ""
    var name: String = ""
    var apPaterno: String = ""
    var apMaterno: String = ""
    var hobby: String = ""
    var job: String = ""
    var tel: String = ""
    var emergencyContact: String = ""
    var email: String = ""
    var birthDay: Date = Date()
    var statusAccount: Int = 0
    var createdDate: Date = Date()
    var updateDate: Date = Date()

    fun toModel(): MembersModel {
        return MembersModel(
            uid, name, apPaterno, apMaterno, hobby, job,
            tel, emergencyContact, email, birthDay, statusAccount, createdDate, updateDate
        )
    }
}