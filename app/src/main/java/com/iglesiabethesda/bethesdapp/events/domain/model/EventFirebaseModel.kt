package com.iglesiabethesda.bethesdapp.events.domain.model

import java.util.Date

class EventFirebaseModel {
    val uidEvent: String = ""
    val dateEvent: Date = Date()
    val titleEvent: String = ""
    val descriptionEvent: String = ""
    val priorityEvent: Int = 0
    val userUidBy: String = ""
    val memberUidBy: String = ""
    val notifyAllUsers: Boolean = false
    val notifySelectedUsers: List<String> = emptyList()
    val createNameBy: String = ""
    val createDate: Date = Date()
    val updateDate: Date = Date()


    fun toModel(): EventModel {
        return EventModel(
            uidEvent, dateEvent, titleEvent,
            descriptionEvent, priorityEvent,
            userUidBy, memberUidBy, notifyAllUsers,
            notifySelectedUsers, createNameBy,
            createDate, updateDate
        )
    }
}