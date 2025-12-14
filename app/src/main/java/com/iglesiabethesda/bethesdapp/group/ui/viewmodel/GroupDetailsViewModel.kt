package com.iglesiabethesda.bethesdapp.group.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.domain.usecase.GetMembersListByUIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    private val getMembersListByUidUseCase: GetMembersListByUIdUseCase
): ViewModel() {

    private val _getMembers = mutableStateOf<Result<List<MembersModel>>?>(null)
    val getMembers: State<Result<List<MembersModel>>?> = _getMembers

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    var groupUid by mutableStateOf("")
    var groupName by mutableStateOf("")
    var descrip by mutableStateOf("")
    var statusGroup by mutableStateOf("")
    var mm by mutableStateOf("")

    fun getMember(list: List<String>) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getMembersListByUidUseCase.invoke(list)
            _getMembers.value = result

            _isLoading.value = false
        }
    }

}