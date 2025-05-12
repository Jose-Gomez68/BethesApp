package com.iglesiabethesda.bethesdapp.Login.domain

import com.iglesiabethesda.bethesdapp.data.network.AuthenticationService
import com.iglesiabethesda.bethesdapp.data.response.LoginResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authenticationService: AuthenticationService) {

    suspend operator fun invoke(email:String, password: String): LoginResult =
        authenticationService.login(email, password)

}