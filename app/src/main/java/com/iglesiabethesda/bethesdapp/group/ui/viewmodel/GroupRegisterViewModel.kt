package com.iglesiabethesda.bethesdapp.group.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.domain.usecase.GetMembersListUseCase
import com.iglesiabethesda.bethesdapp.util.SharedPreferencesConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupRegisterViewModel @Inject
constructor(
    private val getMembersListUseCase: GetMembersListUseCase,
    private val sharedPrf: SharedPreferencesConfig
): ViewModel() {

    private val _getMembers = mutableStateOf<Result<List<MembersModel>>?>(null)
    val getMembers: State<Result<List<MembersModel>>?> = _getMembers

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun getMember() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getMembersListUseCase.invoke(null)
            _getMembers.value = result

            _isLoading.value = false
        }
    }

}