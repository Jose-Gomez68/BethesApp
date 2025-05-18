package com.iglesiabethesda.bethesdapp.Login.domain

import com.iglesiabethesda.bethesdapp.data.network.AuthenticationService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VerifyEmailUseCase @Inject constructor(private val authenticationService: AuthenticationService) {

    operator fun invoke(): Flow<Boolean> = authenticationService.verifiedAccount

}