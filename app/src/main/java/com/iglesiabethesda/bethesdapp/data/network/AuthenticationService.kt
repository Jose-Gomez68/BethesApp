package com.iglesiabethesda.bethesdapp.data.network

import com.iglesiabethesda.bethesdapp.data.response.LoginResult
import kotlinx.coroutines.delay
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationService @Inject constructor(private val firebase: FirebaseClient)  {

    val verifiedAccount: Flow<Boolean> = flow {
        while (true) {
            val verified = verifyEmailIsVerified()
            emit(verified)
            delay(1000)
        }
    }

    suspend fun login(email: String, password: String): LoginResult = runCatching {
        firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }.toLoginResult()

    suspend fun createAccount(email: String, password: String, userName: String): AuthResult? {
        // Crear usuario
        val authResult = firebase.auth.createUserWithEmailAndPassword(email, password).await()

        // Obtener el usuario recién creado
        val user = authResult.user

        // Actualizar el perfil con el displayName
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(userName)
            .build()

        user?.updateProfile(profileUpdates)?.await()

        return authResult
        //return firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun sendVerificationEmail() = runCatching {
        firebase.auth.currentUser?.sendEmailVerification()?.await() ?: false
    }.isSuccess

    private suspend fun verifyEmailIsVerified(): Boolean {
        firebase.auth.currentUser?.reload()?.await()
        return firebase.auth.currentUser?.isEmailVerified ?: false
    }

    private fun Result<AuthResult>.toLoginResult() = when (val result = getOrNull()) {
        null -> LoginResult.Error
        else -> {
            val userId = result.user
            checkNotNull(userId)
            LoginResult.Success(result.user?.isEmailVerified ?: false)
        }
    }

}