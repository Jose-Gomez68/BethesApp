package com.iglesiabethesda.bethesdapp.Login.domain

import androidx.lifecycle.ViewModel
import com.iglesiabethesda.bethesdapp.data.network.AuthenticationService
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {

    suspend operator fun invoke (email:String) = authenticationService.sendPasswordResetEmail(email)

}