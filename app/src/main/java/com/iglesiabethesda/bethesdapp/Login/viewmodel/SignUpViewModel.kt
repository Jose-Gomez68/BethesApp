package com.iglesiabethesda.bethesdapp.Login.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.Login.CreateUserAccountModel
import com.iglesiabethesda.bethesdapp.Login.domain.CreatedUserAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createdUserAccountUseCase: CreatedUserAccountUseCase
): ViewModel(){

    var etSignupMemberCode by mutableStateOf("")
    var etSignupEmail by mutableStateOf("")
    var etSignupPass by mutableStateOf("")
    var etSignupRepeatPass by mutableStateOf("")

    var memberCodeError by mutableStateOf<String?>(null)
    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)
    var repeatPasswordError by mutableStateOf<String?>(null)

    var isUserCreated by mutableStateOf(false)
        private set

    var isUserExist by mutableStateOf(false)
        private set


    fun registerUserAccount() {

        val user = CreateUserAccountModel(
            etSignupMemberCode,
            etSignupEmail,
            etSignupPass,
            etSignupRepeatPass
        )

        viewModelScope.launch {
            val create = createdUserAccountUseCase(user)
           /* if (create) {
                Log.e("EXITO SE CREO","CHINGON")
                isUserCreated = true
            }else{
                Log.e("ERROOORRRR","NOO LA POLITZIA")
                isUserCreated = false
            }*/
            when (create) {
                true -> {
                    isUserCreated = true
                }
                false -> {
                    isUserCreated = false
                }
                null -> {
                    isUserExist = true
                    // Aquí puedes agregar un flag o mensaje especial
                }
            }

        }

    }

    fun validateForm(): Boolean {
        var isValid = true

        emailError = null
        passwordError = null
        repeatPasswordError = null

        if (etSignupEmail.isBlank()) {
            emailError = "El correo no puede estar vacío"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etSignupEmail).matches()) {
            emailError = "Formato de correo inválido"
            isValid = false
        }

        if (etSignupPass.isBlank()) {
            passwordError = "La contraseña no puede estar vacía"
            isValid = false
        }

        if (etSignupRepeatPass.isBlank()) {
            repeatPasswordError = "Debe repetir la contraseña"
            isValid = false
        } else if (etSignupPass != etSignupRepeatPass) {
            repeatPasswordError = "Las contraseñas no coinciden"
            isValid = false
        }

        return isValid
    }

    fun resetIsExistUser() {
        isUserExist = false
    }

}