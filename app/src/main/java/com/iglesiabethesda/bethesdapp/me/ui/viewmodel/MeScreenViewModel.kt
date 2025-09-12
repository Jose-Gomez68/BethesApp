package com.iglesiabethesda.bethesdapp.me.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.me.domain.MeScreenLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeScreenViewModel @Inject constructor(
    val meScreenLogoutUseCase: MeScreenLogoutUseCase
): ViewModel() {

    private val _logoutState = MutableStateFlow<LogoutState>(LogoutState.Idle)
    val logoutState: StateFlow<LogoutState> = _logoutState

    fun logout() {
        viewModelScope.launch {
            _logoutState.value = LogoutState.Loading
            val result = meScreenLogoutUseCase.invoke()

            _logoutState.value = if (result.isSuccess) {
                delay(3000)
                LogoutState.Success

            } else {
                LogoutState.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
            }
        }
    }

}

sealed class LogoutState {
    object Idle : LogoutState()
    object Loading : LogoutState()
    object Success : LogoutState()
    data class Error(val message: String) : LogoutState()
}