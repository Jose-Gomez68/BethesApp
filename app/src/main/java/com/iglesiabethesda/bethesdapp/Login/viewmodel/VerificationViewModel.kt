package com.iglesiabethesda.bethesdapp.Login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.iglesiabethesda.bethesdapp.Login.domain.SendEmailVerificationUseCase
import com.iglesiabethesda.bethesdapp.Login.domain.UpdateUserStatusAccount
import com.iglesiabethesda.bethesdapp.Login.domain.VerifyEmailUseCase
import com.iglesiabethesda.bethesdapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    val verifyEmailUseCase: VerifyEmailUseCase,
    val updateUserStatusAccount: UpdateUserStatusAccount
): ViewModel(){

    private val _navigateToVerifyAccount = MutableLiveData<Event<Boolean>>()
    val navigateToVerifyAccount: LiveData<Event<Boolean>>
        get() = _navigateToVerifyAccount

    private val _showContinueButton = MutableLiveData<Event<Boolean>>()
    val showContinueButton: LiveData<Event<Boolean>>
        get() = _showContinueButton

    init {
        viewModelScope.launch { sendEmailVerificationUseCase() }
        viewModelScope.launch {
            verifyEmailUseCase()
                .catch {
                    Timber.i("Verification Account Error: ${it.message}")
                }
                .collect { verification ->
                    if (verification){
                        _showContinueButton.value = Event(verification)
                        updateUserStatusAccount.invoke(getCurrentUserUid().toString())
                    }
                }
        }
    }

    fun onGoToHomeOrLogin() {
        _navigateToVerifyAccount.value = Event(true)
    }

    fun getCurrentUserEmail(): String? {
        return FirebaseAuth.getInstance().currentUser?.email
    }

    fun getCurrentUserUid(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

}