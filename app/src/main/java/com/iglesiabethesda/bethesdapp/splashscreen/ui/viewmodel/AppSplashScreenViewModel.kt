package com.iglesiabethesda.bethesdapp.splashscreen.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.data.response.LoginResult
import com.iglesiabethesda.bethesdapp.splashscreen.domain.AppSplashScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSplashScreenViewModel @Inject constructor(
    private val splashUseCase: AppSplashScreenUseCase
):  ViewModel() {

    private val _sessionStatus = mutableStateOf<LoginResult?>(null)
    val sessionStatus: State<LoginResult?> = _sessionStatus

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            val result = splashUseCase.invoke()
            _sessionStatus.value = result
        }
    }

}