package com.iglesiabethesda.bethesdapp.members.domain.model

data class MemberAttendance(
    val member: MembersModel,
    val isPresent: Boolean = false,
    val isLate: Boolean = false
)
