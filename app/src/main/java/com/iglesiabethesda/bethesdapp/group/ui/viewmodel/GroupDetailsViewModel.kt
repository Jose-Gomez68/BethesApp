package com.iglesiabethesda.bethesdapp.group.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModel
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.domain.usecase.GetMembersListByUIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
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
    var statusGroup by mutableStateOf(0)
    var listMember by mutableStateOf(0)
    var createdDate by mutableStateOf(Date())
    var endDate by mutableStateOf<Date?>(null)

    fun getMember(list: List<String>) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getMembersListByUidUseCase.invoke(list)
            _getMembers.value = result

            _isLoading.value = false
        }
    }

    fun loadGroup(group: GroupModel) {
        groupUid = group.uid
        groupName = group.name
        descrip = group.description
        statusGroup = group.statusGroup
        listMember = group.listMembers.size
        createdDate = group.createdDate
        endDate = group.endDate
    }

}