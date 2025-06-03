package com.iglesiabethesda.bethesdapp.Login.domain

import android.util.Log
import com.iglesiabethesda.bethesdapp.Login.CreateUserAccountModel
import com.iglesiabethesda.bethesdapp.Login.ui.model.UserSignIn
import com.iglesiabethesda.bethesdapp.data.network.AuthenticationService
import com.iglesiabethesda.bethesdapp.data.network.UserService
import java.util.Date
import javax.inject.Inject

class CreatedUserAccountUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
){

    suspend operator fun invoke(userAccount: CreateUserAccountModel): Boolean {
        var result = false
        val getMemberRegisters = userService.getMemberByMemberCode(userAccount.memberCode)
        val accountCreated =  authenticationService.createAccount(userAccount.email, userAccount.password,
            "${getMemberRegisters!!.name} ${getMemberRegisters.apPaterno} ${getMemberRegisters.apMaterno}") != null

        if (!getMemberRegisters?.name.isNullOrEmpty()){
            if (accountCreated){
                val userSignIn = UserSignIn(
                    getMemberRegisters.uid,
                    "${getMemberRegisters!!.name} ${getMemberRegisters.apPaterno} ${getMemberRegisters.apMaterno}",
                    "${getMemberRegisters!!.name} ${getMemberRegisters.apPaterno}",
                    userAccount.email,
                    userAccount.password,
                    userAccount.repeatPassword,
                    1,
                    Date(),
                    Date()
                )
                userService.createUserTable(userSignIn)
                userService.updateMemberEmailByUid(getMemberRegisters.uid, userAccount.email)
                result = true
            }
        }

        return result
    }

}