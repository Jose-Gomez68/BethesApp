package com.iglesiabethesda.bethesdapp.me.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModel
import com.iglesiabethesda.bethesdapp.group.domain.usecase.GetGroupsByUidUserUseCase
import com.iglesiabethesda.bethesdapp.me.domain.MeScreenLogoutUseCase
import com.iglesiabethesda.bethesdapp.me.ui.model.UserUiStateModel
import com.iglesiabethesda.bethesdapp.util.SharedPreferencesConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeScreenViewModel @Inject constructor(
    private val meScreenLogoutUseCase: MeScreenLogoutUseCase,
    private val pref: SharedPreferencesConfig,
    private val groupByIdUser: GetGroupsByUidUserUseCase
): ViewModel() {

    private val _logoutState = MutableStateFlow<LogoutState>(LogoutState.Idle)
    val logoutState: StateFlow<LogoutState> = _logoutState

    private val _getGroups = mutableStateOf<Result<List<GroupModel>>?>(null)
    val getGroups: State<Result<List<GroupModel>>?> = _getGroups

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    /*private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _userType = MutableStateFlow("")
    val userType: StateFlow<String> = _userType

    private val _uidUser = MutableStateFlow("")
    val uidUser: StateFlow<String> = _uidUser

    private val _uidMember = MutableStateFlow("")
    val uidMember: StateFlow<String> = _uidMember

    private val _userEmail = MutableStateFlow("")
    val userEmail: StateFlow<String> = _userEmail*/

    private val _uiState = MutableStateFlow(UserUiStateModel())
    val uiState: StateFlow<UserUiStateModel> = _uiState

    init {
        _uiState.value = UserUiStateModel(
            userName = pref.getUserName(),
            name = pref.getMemberName(),
            userType = pref.getUserType(),
            uidUser = pref.getUserUid(),
            uidMember = pref.getMemberUid(),
            email = pref.getEmail(),//terminar de llenar los modelos con los nuevo del preferences
            birthDay = pref.getBirthDay(),
            hobby = pref.getHobby(),
            job = pref.getJob()

        )
        getGroups()
        /*_userName.value = pref.getUserName()
        _name.value = pref.getMemberName()
        _userType.value = pref.getUserType()
        _uidUser.value = pref.getUserUid()
        _uidMember.value = pref.getMemberUid()
        _userEmail.value = pref.getEmail()*/
    }

    fun getGroups() {

        viewModelScope.launch {
            _isLoading.value = true
            val result = groupByIdUser.invoke(pref.getMemberUid())
            _getGroups.value = result
            result.onSuccess {
                Log.e("AQUI", ""+it)//revisar por que no llegan nada de listado
            }
            _isLoading.value = false
        }

    }

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

    fun clearPreferences() {
        pref.clearShredPref()
    }

}

sealed class LogoutState {
    object Idle : LogoutState()
    object Loading : LogoutState()
    object Success : LogoutState()
    data class Error(val message: String) : LogoutState()
}