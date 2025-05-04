package com.iglesiabethesta.bethestaapp.Login.domain

import com.iglesiabethesta.bethestaapp.data.network.AuthenticationService
import com.iglesiabethesta.bethestaapp.data.response.LoginResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authenticationService: AuthenticationService) {

    suspend operator fun invoke(email:String, password: String): LoginResult =
        authenticationService.login(email, password)

}