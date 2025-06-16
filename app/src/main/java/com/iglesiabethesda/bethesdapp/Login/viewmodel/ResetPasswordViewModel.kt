package com.iglesiabethesda.bethesdapp.Login.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.Login.domain.ResetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<ResetPasswordUiState>(ResetPasswordUiState.Idle)
    val uiState: StateFlow<ResetPasswordUiState> = _uiState
    var etResetPassEmail by mutableStateOf("")
    var tvResetPassEmailError by mutableStateOf<String?>(null)

    fun sendResetPassEmail() {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etResetPassEmail).matches()) {
            _uiState.value = ResetPasswordUiState.InvalidEmail
            return
        }

        viewModelScope.launch {
            _uiState.value = ResetPasswordUiState.Loading
            val result = resetPasswordUseCase(etResetPassEmail)
            _uiState.value = if (result) {
                ResetPasswordUiState.Success
            } else {
                ResetPasswordUiState.Error
            }
        }

    }

    fun resetState() {
        _uiState.value = ResetPasswordUiState.Idle
    }

    fun validateForm(): Boolean {
        var isValid = true

        tvResetPassEmailError = null

        if (etResetPassEmail.isBlank()) {
            tvResetPassEmailError = "El correo no puede estar vacío"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etResetPassEmail).matches()) {
            tvResetPassEmailError = "Formato de correo inválido"
            isValid = false
        }

        return isValid
    }

}


sealed class ResetPasswordUiState {
    object Idle : ResetPasswordUiState()
    object Loading : ResetPasswordUiState()
    object Success : ResetPasswordUiState()
    object Error : ResetPasswordUiState()
    object InvalidEmail : ResetPasswordUiState()
}