package com.iglesiabethesda.bethesdapp.data.network

import com.google.firebase.FirebaseNetworkException
import com.iglesiabethesda.bethesdapp.data.response.LoginResult
import kotlinx.coroutines.delay
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidUserException
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

    /* ANTERIOR FUNCION SOLO MANEJA ERROR DE LOGIN
    suspend fun login(email: String, password: String): LoginResult = runCatching {
        firebase.auth.signInWithEmailAndPassword(email, password).await()
    }.toLoginResult()*/

    suspend fun login(email: String, password: String): LoginResult = try {
        val result = firebase.auth.signInWithEmailAndPassword(email, password).await()
        val user = result.user
        if (user != null) {
            LoginResult.Success(user.isEmailVerified)
        } else {
            LoginResult.Error
        }
    } catch (e: FirebaseAuthInvalidUserException) {
        if (e.errorCode == "ERROR_USER_DISABLED") {
            LoginResult.DisabledAccount // <- tú defines este estado
        } else {
            LoginResult.Error
        }
    } catch (e: FirebaseNetworkException) {
        LoginResult.NetworkError
    } catch (e: Exception) {
        LoginResult.Error
    }


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

    /*SI HAY UNA SESION ACTIVA VERIFICA
    * 1. SI LA CUENTA ESTA ACTIVA O CONFRIMADA O VERIFICADA DESDE LA URL QUE MANDA GOOGLE
    * 2. SI LA CUENTA NO ESTA VERIFICADA
    * 3. SI NO HAY CUENTA CON SESION INICIADA*/
    suspend fun getActiveSessionStatus(): LoginResult = runCatching {
        val user = firebase.auth.currentUser
        if (user != null) {
            user.reload().await() // aquí puede fallar si no hay internet
            LoginResult.Success(user.isEmailVerified)
        } else {
            LoginResult.Error
        }
    }.getOrElse { throwable ->
        if (throwable is FirebaseNetworkException) {
            LoginResult.NetworkError
        } else {
            LoginResult.Error
        }
    }

    /*ahora verifica la conxicon a intenrt
    suspend fun getActiveSessionStatus(): LoginResult {
        val user = firebase.auth.currentUser

        return if (user != null) {
            // Recargar el estado del usuario (para actualizar isEmailVerified)
            user.reload().await()
            LoginResult.Success(user.isEmailVerified)
        } else {
            LoginResult.Error
        }
    }*/

    /*SEND RESET PASSWORD*/
    suspend fun sendPasswordResetEmail(email: String): Boolean = runCatching {
        firebase.auth.sendPasswordResetEmail(email).await()
        true
    }.getOrElse {
        false
    }

    /*CIERRE DE SESION*/
    fun logout() {
        firebase.auth.signOut()
    }

    /**
     * Devuelve el UID del usuario actualmente logueado
     */
    fun getCurrentUserUid(): String? {
        return firebase.auth.currentUser?.uid
    }

    /**
     * Devuelve el email del usuario actualmente logueado
     */
    fun getCurrentUserEmail(): String? {
        return firebase.auth.currentUser?.email
    }

    /**
     * Devuelve el nombre de usuario (displayName) del usuario logueado
     */
    fun getCurrentUserName(): String? {
        return firebase.auth.currentUser?.displayName
    }


}