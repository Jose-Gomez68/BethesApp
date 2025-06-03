package com.iglesiabethesda.bethesdapp.splashscreen.domain

import com.iglesiabethesda.bethesdapp.data.network.AuthenticationService
import com.iglesiabethesda.bethesdapp.data.response.LoginResult
import javax.inject.Inject

class AppSplashScreenUseCase @Inject constructor(
    private val authService: AuthenticationService
){

    suspend operator fun invoke(): LoginResult {
        return authService.getActiveSessionStatus()
    }

}