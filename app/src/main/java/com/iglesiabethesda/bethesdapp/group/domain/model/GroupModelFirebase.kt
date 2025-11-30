package com.iglesiabethesda.bethesdapp.group.domain.model

import java.util.Date

class GroupModelFirebase {
    var uid: String = ""
    var name: String = ""
    var description: String = ""
    var listMembers: List<String> = emptyList()
    var statusGroup: Int = 1 //1-> activo, 2 -> Inactivo = a que cumplio o termino su tiempo, 3 -> Eliminado = que se elimino
    var createdDate: Date = Date()
    var updateDate: Date = Date()
    var endDate: Date? = null

    fun toModel(): GroupModel {
        return GroupModel(
            uid, name, description, listMembers, statusGroup,createdDate, updateDate, endDate
        )
    }
}