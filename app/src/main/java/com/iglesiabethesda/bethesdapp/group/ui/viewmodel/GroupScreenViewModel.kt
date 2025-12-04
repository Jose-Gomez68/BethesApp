package com.iglesiabethesda.bethesdapp.group.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModel
import com.iglesiabethesda.bethesdapp.group.domain.usecase.DeleteGroupUseCase
import com.iglesiabethesda.bethesdapp.group.domain.usecase.GetGroupsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupScreenViewModel @Inject constructor(
    private val groupListUseCase: GetGroupsUseCase,
    private val deleteGroupByUid: DeleteGroupUseCase
): ViewModel(){

    private val _getGroups = mutableStateOf<Result<List<GroupModel>>?>(null)
    val getGroups: State<Result<List<GroupModel>>?> = _getGroups

    private val _getGroupDelete = mutableStateOf<Boolean>(false)
    val getGroupDelete: State<Boolean> = _getGroupDelete

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun getGroups() {

        viewModelScope.launch {
            _isLoading.value = true
            val result = groupListUseCase.invoke()
            _getGroups.value = result

            _isLoading.value = false
        }

    }

    fun deleteGroupByUid(uid: String) {

        viewModelScope.launch {
            _isLoading.value = true
            val result = deleteGroupByUid.invoke(uid)
            result.onSuccess {
                _getGroupDelete.value = false
            }.onFailure {
                _isLoading.value = false
                _getGroupDelete.value = true
            }

            _isLoading.value = false

        }

    }

    fun resetShowDeleteError() {
        _getGroupDelete.value = false
    }

}