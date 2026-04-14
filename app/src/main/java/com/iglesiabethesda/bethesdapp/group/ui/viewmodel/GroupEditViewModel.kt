package com.iglesiabethesda.bethesdapp.group.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModel
import com.iglesiabethesda.bethesdapp.group.domain.usecase.GetGroupByIdUseCase
import com.iglesiabethesda.bethesdapp.group.domain.usecase.UpdateGroupUseCase
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.domain.usecase.GetAllMembersListExcepByUidUseCase
import com.iglesiabethesda.bethesdapp.members.domain.usecase.GetMembersListByUIdUseCase
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class GroupEditViewModel @Inject constructor(
    private val getMembersListByUidUseCase: GetMembersListByUIdUseCase,
    private val getAllMembersListExcepByUidUseCase: GetAllMembersListExcepByUidUseCase,
    private val updateGroupUseCase: UpdateGroupUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase
) : ViewModel(){

    /** UI STATE */
    var isGroupEdited by mutableStateOf(false)
        private set

    val isLoading = mutableStateOf(false)

    var showErrorDialog by mutableStateOf(false)
        private set

    var showErrorDialogGetMembers by mutableStateOf(false)
        private set

    var showErrorDialogInternet by mutableStateOf(false)
        private set

    /** FORM */
    var name by mutableStateOf("")
    var descrip by mutableStateOf("")
    private val _getMembers = mutableStateOf<Result<List<MembersModel>>?>(null)
    val getMembers: State<Result<List<MembersModel>>?> = _getMembers
    var listMembersGson = mutableStateListOf<MembersModel>()
    var response = mutableStateOf<GroupModel?>(null)
    var statusGroup by mutableStateOf(0)
    var createdDate by mutableStateOf(Date())
    val utilsFunctions = UtilsFunctions()

    var nameError by mutableStateOf<String?>(null)
    var descripError by mutableStateOf<String?>(null)
    var listMembersError by mutableStateOf<String?>(null)
    var listMembersError2 by mutableStateOf<String?>(null)

    /** DATA */
    lateinit var groupId: String
        private set

    fun initGroup(groupJson: GroupModel) {
        val group = groupJson

        groupId = group.uid
        name = group.name
        descrip = group.description
        statusGroup = group.statusGroup
        createdDate = group.createdDate

        viewModelScope.launch {
            try {
                isLoading.value = true
                getMembersListByUidUseCase.invoke(group.listMembers).onSuccess { it ->
                    listMembersGson.addAll(it)
                }

                val result = getAllMembersListExcepByUidUseCase.invoke(null)
                _getMembers.value = result

                result.onSuccess {
                    Log.e("AQUI", it.toString())
                }


            }catch (e: Exception){
                showErrorDialogGetMembers = true
            } finally {
                isLoading.value = false
            }
        }
    }

    fun editGroup() {
        viewModelScope.launch {
            isLoading.value = true
            try {

                var uidListMember: List<String>
                uidListMember = listMembersGson.map {
                    it.uid
                }

                val groupU = GroupModel(
                    uid = groupId,
                    name = name,
                    description = descrip,
                    listMembers = uidListMember,
                    statusGroup = statusGroup,
                    createdDate = createdDate,
                    updateDate = utilsFunctions.getCurrentDateTime()
                )

                var resultSend = updateGroupUseCase(groupU)

                if (resultSend)
                    response.value = groupU

                /*updateGroupUseCase(
                    GroupModel(
                        uid = groupId,
                        name = name,
                        description = descrip,
                        listMembers = listMembers.map { it.uid },
                        statusGroup = 1,
                        updateDate = UtilsFunctions().getCurrentDateTime(),

                    )
                )*/
                isGroupEdited = true
            } catch (e: Exception) {
                showErrorDialog = true
            } finally {
                isLoading.value = false
            }
        }
    }

    fun closeGroup() {
        // status = 0 / fecha cierre
    }

    fun validateForm(): Boolean {
        nameError = null
        descripError = null
        listMembersError = null

        var isValid = true

        if (name.isBlank()) {
            nameError = "El nombre no puede estar vacío"
            isValid = false
        }

        if (descrip.isBlank()) {
            descripError = "La descripción no puede estar vacía"
            isValid = false
        }

        if (listMembersGson.size < 2) {
            listMembersError = "Debe haber al menos 2 integrantes"
            isValid = false
        }

        return isValid
    }

    fun resetShowErrorDialog() {
        showErrorDialog = false
    }

    fun resetShowErrorGetMembersDialog() {
        showErrorDialogGetMembers = false
    }

    fun resetShowErrorDialogInternet() {
        showErrorDialogInternet = false
    }

}