package com.iglesiabethesda.bethesdapp.me.domain

import com.iglesiabethesda.bethesdapp.data.network.AuthenticationService
import com.iglesiabethesda.bethesdapp.data.network.UserService
import javax.inject.Inject

class MeScreenLogoutUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService,
) {

    suspend operator fun invoke(): Result<Unit> {
        return try {
            authenticationService.logout()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}