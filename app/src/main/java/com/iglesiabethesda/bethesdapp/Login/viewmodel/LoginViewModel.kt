package com.iglesiabethesda.bethesdapp.Login.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.Login.domain.LoginUseCase
import com.iglesiabethesda.bethesdapp.Login.ui.LoginViewState
import com.iglesiabethesda.bethesdapp.Login.ui.model.UserLogin
import com.iglesiabethesda.bethesdapp.Login.ui.model.UserModel
import com.iglesiabethesda.bethesdapp.data.response.LoginResult
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase
) : ViewModel() {

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    private val _navigateToDetails = MutableLiveData<Event<Boolean>>()
    val navigateToDetails: LiveData<Event<Boolean>>
        get() = _navigateToDetails

    private val _navigateToForgotPassword = MutableLiveData<Event<Boolean>>()
    val navigateToForgotPassword: LiveData<Event<Boolean>>
        get() = _navigateToForgotPassword

    private val _navigateToSignIn = MutableLiveData<Event<Boolean>>()
    val navigateToSignIn: LiveData<Event<Boolean>>
        get() = _navigateToSignIn

    private val _navigateToVerifyAccount = MutableLiveData<Event<Boolean>>()
    val navigateToVerifyAccount: LiveData<Event<Boolean>>
        get() = _navigateToVerifyAccount

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState: StateFlow<LoginViewState>
        get() = _viewState

    private var _showErrorDialog = MutableLiveData(UserLogin())
    val showErrorDialog: LiveData<UserLogin>
        get() = _showErrorDialog

    private var _showErrorNetworkDialog = MutableLiveData<Boolean>()
    val showErrorNetworkDialog: LiveData<Boolean>
        get() = _showErrorNetworkDialog

    private var _showDisableAccountDialog = MutableLiveData<Boolean>()
    val showDisableAccountDialog: LiveData<Boolean>
        get() = _showDisableAccountDialog

    private val _getUserModel = MutableLiveData<UserModel?>()
    val getUserModel: LiveData<UserModel?>
        get() = _getUserModel

    private val _getMemberModel = MutableLiveData<MembersModel?>()
    val getMemberModel: LiveData<MembersModel?>
        get() = _getMemberModel

    /*metodo que valida los campos del correo y contra
    * al iniciar sesion en el login */
    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _viewState.value = LoginViewState(isLoading = true)
            when (val result = loginUseCase(email, password)) {
                LoginResult.Error -> {
                    _showErrorDialog.value =
                        UserLogin(email = email, password = password, showErrorDialog = true)
                    _viewState.value = LoginViewState(isLoading = false)
                }
                is LoginResult.Success -> {
                    if (result.verified) {
                        val member = loginUseCase.getUserByEmailMembers(email)
                        val user = loginUseCase.getUserByEmailUser(email)

                        if (member != null && user != null) {
                            _getMemberModel.postValue(member)
                            _getUserModel.postValue(user)
                            _navigateToDetails.value = Event(true) // ahora sí, ya con datos listos
                        } else {
                            Log.e("DEBUG", "Member o User llegaron nulos")
                        }

                    } else {
                        _navigateToVerifyAccount.value = Event(true)
                    }
                }

                is LoginResult.NetworkError -> {
                    _showErrorNetworkDialog.value = true
                }

                is LoginResult.DisabledAccount -> {
                    // Aquí controlas cuando Firebase authentication devuelve ERROR_USER_DISABLED
                    _showDisableAccountDialog.value = true
                }

            }
            _viewState.value = LoginViewState(isLoading = false)
        }
    }

    fun onFieldsChanged(email: String, password: String) {
        _viewState.value = LoginViewState(
            isValidEmail = isValidEmail(email),
            isValidPassword = isValidPassword(password)
        )
    }

    fun onForgotPasswordSelected() {
        _navigateToForgotPassword.value = Event(true)
    }

    fun onSignInSelected() {
        _navigateToSignIn.value = Event(true)
    }

    private fun isValidEmail(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()

    private fun isValidPassword(password: String): Boolean =
        password.length >= MIN_PASSWORD_LENGTH || password.isEmpty()

    fun clearErrorDialog() {
        _showErrorDialog.value = UserLogin(showErrorDialog = false)
    }

    fun clearNetworkErrorDialog() {
        _showErrorNetworkDialog.value = false
    }

    fun clearDisnableDialog() {
        _showDisableAccountDialog.value = false
    }

}