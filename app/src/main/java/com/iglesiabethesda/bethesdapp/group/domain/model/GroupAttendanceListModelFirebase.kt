package com.iglesiabethesda.bethesdapp.group.domain.model

import com.iglesiabethesda.bethesdapp.members.domain.model.AttendanceItem
import com.iglesiabethesda.bethesdapp.members.domain.model.AttendanceListModel
import java.util.Date

class GroupAttendanceListModelFirebase {
    val uid: String = ""
    val uidGroup: String = ""
    val list: List<AttendanceItem> = emptyList()
    val createDate: Date = Date()
    val updateDate: Date = Date()

    fun toModel(): AttendanceListModel {
        return AttendanceListModel(
            uid,
            uidGroup,
            list,
            createDate,
            updateDate
        )
    }

}
