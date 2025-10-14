package com.iglesiabethesda.bethesdapp.events.domain.model

import java.util.Date

data class EventModel(
    val uidEvent: String,
    val dateEvent: Date, //fecha a la que se asigno el evento
    val titleEvent: String,
    val descriptionEvent: String,
    val priorityEvent: Int,
    val userUidBy: String, //uid del usuario
    val memberUidBy: String, //uid del miembro
    val notifyAllUsers: Boolean,
    val notifySelectedUsers: List<String> = emptyList(), // lista de UIDs de usuarios seleccionados
    val createNameBy: String, //nombre del ususario
    val createDate: Date,
    val updateDate: Date,

)
