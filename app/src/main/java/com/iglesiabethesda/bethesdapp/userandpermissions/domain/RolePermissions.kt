package com.iglesiabethesda.bethesdapp.userandpermissions.domain

import com.iglesiabethesda.bethesdapp.userandpermissions.enums.Permission
import com.iglesiabethesda.bethesdapp.userandpermissions.enums.UserRole

fun UserRole.permissions(): Set<Permission> = when (this) {
    UserRole.ADMIN -> setOf(
        Permission.CREATE,
        Permission.DELETE,
        Permission.EDIT,
        Permission.VIEW
    )
    UserRole.SUPERVISOR -> setOf(
        Permission.CREATE,
        Permission.EDIT,
        Permission.VIEW
    )
    UserRole.USER -> setOf(
        Permission.VIEW
    )
}