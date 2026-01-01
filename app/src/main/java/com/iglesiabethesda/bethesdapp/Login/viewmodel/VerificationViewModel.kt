package com.iglesiabethesda.bethesdapp.Login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.iglesiabethesda.bethesdapp.Login.domain.LoginUseCase
import com.iglesiabethesda.bethesdapp.Login.domain.SendEmailVerificationUseCase
import com.iglesiabethesda.bethesdapp.Login.domain.UpdateUserStatusAccount
import com.iglesiabethesda.bethesdapp.Login.domain.VerifyEmailUseCase
import com.iglesiabethesda.bethesdapp.Login.ui.model.UserModel
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
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
    val updateUserStatusAccount: UpdateUserStatusAccount,
    val loginUseCase: LoginUseCase
): ViewModel(){

    private val _navigateToVerifyAccount = MutableLiveData<Event<Boolean>>()
    val navigateToVerifyAccount: LiveData<Event<Boolean>>
        get() = _navigateToVerifyAccount

    private val _showContinueButton = MutableLiveData<Event<Boolean>>()
    val showContinueButton: LiveData<Event<Boolean>>
        get() = _showContinueButton

    private val _getUserModel = MutableLiveData<UserModel?>()
    val getUserModel: LiveData<UserModel?>
        get() = _getUserModel

    private val _getMemberModel = MutableLiveData<MembersModel?>()
    val getMemberModel: LiveData<MembersModel?>
        get() = _getMemberModel

    init {
        viewModelScope.launch { sendEmailVerificationUseCase() }
        viewModelScope.launch {
            verifyEmailUseCase()
                .catch {
                    Timber.i("Verification Account Error: ${it.message}")
                }
                .collect { verification ->
                    if (verification){
                        val member = loginUseCase.getUserByEmailMembers(getCurrentUserEmail()!!)
                        val user = loginUseCase.getUserByEmailUser(getCurrentUserEmail()!!)
                        if (member != null && user != null) {
                            _getMemberModel.postValue(member)
                            _getUserModel.postValue(user)

                        } else {
                            Log.e("DEBUG", "Member o User llegaron nulos")
                        }
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