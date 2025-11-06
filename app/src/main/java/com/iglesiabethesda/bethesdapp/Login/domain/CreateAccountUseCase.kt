package com.iglesiabethesda.bethesdapp.Login.domain

import com.iglesiabethesda.bethesdapp.Login.ui.model.UserSignIn
import com.iglesiabethesda.bethesdapp.data.network.AuthenticationService
import com.iglesiabethesda.bethesdapp.data.network.UserService
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase
){

    /**
     * HAY QUE MODIFICAR LA FUNCION  YA QUE
     * LOS USUARIOS SE CREARAN APARTE CON SOLO CORREO EL ID DE MEMBERS
     * Y LA PASSWORD.
     * por que aparte son los miembros que no todos
     * tendran ususario */

    suspend operator fun invoke(userSignIn: UserSignIn, membersModel: MembersModel): Boolean {
        val accountCreated =
            authenticationService.createAccount(userSignIn.email, userSignIn.password,
                "${membersModel.name} ${membersModel.apPaterno} ${membersModel.apMaterno}") != null
        return if (accountCreated) {
            /*TRATAR DE QUE COINCIDAN EL UID DEL REGISTRO CON LA DEL AUTH DE EMAIL*/
            userService.createUserTable(userSignIn)
            userService.createMemberTable(membersModel)
            sendEmailVerificationUseCase.invoke()// SE DEBE QUITAR YA QUE IRA EN LA PANTLLA DE ESPERA
        } else {
            false
        }
    }

}