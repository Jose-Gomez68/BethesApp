package com.iglesiabethesda.bethesdapp.me.domain

import com.iglesiabethesda.bethesdapp.data.network.UserService
import com.iglesiabethesda.bethesdapp.members.data.MembersModel
import javax.inject.Inject

class MeScreenGetDataUseCase @Inject constructor(
    private val userService: UserService
) {

  /*  suspend operator fun invoke(): MembersModel {

        val member: MembersModel = userService.getMemberByMemberCode()

    }*/

}