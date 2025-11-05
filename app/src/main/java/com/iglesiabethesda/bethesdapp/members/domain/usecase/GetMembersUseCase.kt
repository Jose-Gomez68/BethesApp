package com.iglesiabethesda.bethesdapp.members.domain.usecase

import com.iglesiabethesda.bethesdapp.data.network.AuthenticationService
import com.iglesiabethesda.bethesdapp.data.network.UserService
import com.iglesiabethesda.bethesdapp.members.data.MembersModel
import java.util.Date
import javax.inject.Inject

/*ESTTA CLASE OBTENE O BUSCA AL
* MIEMBRO POR SU MEMBERCODE PARA CUANDO SE VINCULA
* EL USUARIO CON EL MIEMBRO*/
class GetMembersUseCase  @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
){

    suspend operator fun invoke(memberCode: String): MembersModel? {
        val member: MembersModel? = userService.getMemberByMemberCode(memberCode)

        return member ?: MembersModel(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                Date(),
                0,
                Date(),
                Date()

        )
    }

}

/*
* suspend operator fun invoke(memberCode: String, userSignIn: UserSignIn): Boolean {
        val member = userService.getMemberByMemberCode(memberCode)
        val createUserAccount = authenticationService.createAccount(userSignIn.email, userSignIn.password,
            "${member!!.name} ${member!!.apPaterno} ${member!!.apMaterno}") != null
            return if (member != null) {
                createUserAccount
        } else {
            false
        }
    }*/