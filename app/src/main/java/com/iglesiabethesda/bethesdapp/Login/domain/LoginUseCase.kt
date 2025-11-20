package com.iglesiabethesda.bethesdapp.Login.domain

import com.iglesiabethesda.bethesdapp.Login.ui.model.UserModel
import com.iglesiabethesda.bethesdapp.data.network.AuthenticationService
import com.iglesiabethesda.bethesdapp.data.network.UserService
import com.iglesiabethesda.bethesdapp.data.response.LoginResult
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import java.util.Date
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
) {

    suspend operator fun invoke(email:String, password: String): LoginResult =
        authenticationService.login(email, password)

    suspend fun getUserUid(): String {
        return authenticationService.getCurrentUserUid()!!
    }

    suspend fun getUserByEmailMembers(email: String): MembersModel?{
        val a = userService.getMemberByEmail(email)
        return a ?: MembersModel(
            "",
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

    suspend fun getUserByEmailUser(email: String): UserModel? {
        val user: UserModel? = userService.getUserByEmail(email)
        return user ?: UserModel(
            "",
            "",
            "",
            "",
            0,
            Date(),
            Date()
        )
    }

}