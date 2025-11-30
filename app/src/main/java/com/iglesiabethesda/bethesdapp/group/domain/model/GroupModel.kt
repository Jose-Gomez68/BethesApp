package com.iglesiabethesda.bethesdapp.group.domain.model

import java.util.Date

data class GroupModel(
    val uid: String = "",
    val name: String,
    val description: String,
    val listMembers: List<String>,
    val statusGroup: Int, //1-> activo, 2 -> Inactivo = a que cumplio o termino su tiempo, 3 -> Eliminado = que se elimino
    val createdDate: Date,
    val updateDate: Date,
    val endDate: Date? = null
)
