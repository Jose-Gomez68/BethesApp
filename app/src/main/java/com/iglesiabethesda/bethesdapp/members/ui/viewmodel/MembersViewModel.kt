package com.iglesiabethesda.bethesdapp.members.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.domain.usecase.DeleteMemberUseCase
import com.iglesiabethesda.bethesdapp.members.domain.usecase.GetMembersListUseCase
import com.iglesiabethesda.bethesdapp.util.SharedPreferencesConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MembersViewModel @Inject constructor(
    private val getMembersListUseCase: GetMembersListUseCase,
    private val sharedPrf: SharedPreferencesConfig,
    private val deleteMemberUseCase: DeleteMemberUseCase
): ViewModel() {

    private val _getMembers = mutableStateOf<Result<List<MembersModel>>?>(null)
    val getMembers: State<Result<List<MembersModel>>?> = _getMembers

    private val _getMembersDelete = mutableStateOf<Boolean>(false)
    val getMembersDelete: State<Boolean> = _getMembersDelete

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun getMember() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getMembersListUseCase.invoke(sharedPrf.getMemberUid())
            _getMembers.value = result

            _isLoading.value = false
        }
    }

    fun deleteMemberByUid(uid:String) {//me guie de EventsScreenViewModel
        viewModelScope.launch {
            _isLoading.value = true
            val result = deleteMemberUseCase.invoke(uid)
            result.onSuccess {
                _getMembersDelete.value = false
            }.onFailure {
                _getMembersDelete.value = true
                //si falla es true y muestra el mensaje de error
            }
            _isLoading.value = false
        }
    }

}