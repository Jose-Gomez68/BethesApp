package com.iglesiabethesda.bethesdapp.Login.viewmodel

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

    fun sendResetPassEmail(email:String) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.value = ResetPasswordUiState.InvalidEmail
            return
        }

        viewModelScope.launch {
            _uiState.value = ResetPasswordUiState.Loading
            val result = resetPasswordUseCase(email)
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

}


sealed class ResetPasswordUiState {
    object Idle : ResetPasswordUiState()
    object Loading : ResetPasswordUiState()
    object Success : ResetPasswordUiState()
    object Error : ResetPasswordUiState()
    object InvalidEmail : ResetPasswordUiState()
}