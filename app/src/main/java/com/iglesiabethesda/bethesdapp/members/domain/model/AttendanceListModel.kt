package com.iglesiabethesda.bethesdapp.members.domain.model

import java.util.Date

data class AttendanceListModel(
    val uid: String,
    val uidGroup: String,
    val list: List<AttendanceItem>,
    val createDate: Date,
    val updateDate: Date
)
