package com.iglesiabethesda.bethesdapp.Login.domain

import androidx.lifecycle.ViewModel
import com.iglesiabethesda.bethesdapp.data.network.AuthenticationService
import javax.inject.Inject

class SendEmailVerificationUseCase @Inject constructor(private val authenticationService: AuthenticationService): ViewModel() {

    /*ESTE CASO DE USO DEBE TENER SU PROPIO VIEWMODEL
    * Y DEBE IR EN LA PANTALLA DE ESPERA MIENTRAS SE
    * VERIFICA LA CUENTA OSEA EN LA ANIMACION PARA MANDAR A LOGIN*/
    suspend operator fun invoke() = authenticationService.sendVerificationEmail()

}