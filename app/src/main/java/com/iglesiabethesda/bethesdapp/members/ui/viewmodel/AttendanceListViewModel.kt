package com.iglesiabethesda.bethesdapp.members.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.members.domain.model.AttendanceItem
import com.iglesiabethesda.bethesdapp.members.domain.model.AttendanceListModel
import com.iglesiabethesda.bethesdapp.members.domain.model.MemberAttendance
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.domain.usecase.AttendanceListUseCase
import com.iglesiabethesda.bethesdapp.members.domain.usecase.GetAllMembersListExcepByUidUseCase
import com.iglesiabethesda.bethesdapp.userandpermissions.domain.permissions
import com.iglesiabethesda.bethesdapp.userandpermissions.enums.Permission
import com.iglesiabethesda.bethesdapp.userandpermissions.enums.UserRole
import com.iglesiabethesda.bethesdapp.util.SharedPreferencesConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AttendanceListViewModel @Inject constructor(
    private val getAllMembersListExcepByUidUseCase: GetAllMembersListExcepByUidUseCase,
    private val sharedPrf: SharedPreferencesConfig,
    private val saveAttendanceList: AttendanceListUseCase
): ViewModel(){

    private val _getMembers = mutableStateOf<Result<List<MembersModel>>?>(null)
    val getMembers: State<Result<List<MembersModel>>?> = _getMembers

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    var isListFinish by mutableStateOf(false)
        private set

    var showErrorDialog by mutableStateOf(false)
        private set

    var listAttendanceGson = mutableStateListOf<AttendanceItem>()
    var uidGroup by mutableStateOf("")
    var createDate by mutableStateOf(Date())


    fun getMember() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getAllMembersListExcepByUidUseCase.invoke(null)
            _getMembers.value = result

            _isLoading.value = false
        }
    }

    fun saveAttenfanceList(members: List<MemberAttendance>) {
        Log.e("LISTADO", "${members}")
        Log.e("LISTADO", "${members.size}")
        members.map {
            Log.e("LISTADO", "${it}")
            Log.e("LISTADO", "${it.member}")
            // todo correcto viene todo el listado y ahi vemos si esta en true el presente es que llego
            //si esta en false es que tiene falta, y falara implementar el retardo
            
        }
        viewModelScope.launch {
            _isLoading.value = true

            var list = AttendanceListModel(
                uid = "",
                uidGroup = uidGroup,
                list = listAttendanceGson,
                createDate = createDate,
                updateDate = Date(),
            )

            var sendResult = saveAttendanceList(list)

            sendResult.onSuccess {
                isListFinish = true
            }.onFailure {
                isListFinish = false

            }

            _isLoading.value = false

            /*try {
                var list = AttendanceListModel(
                    uid = "",
                    uidGroup = uidGroup,
                    list = listAttendanceGson,
                    createDate = createDate,
                    updateDate = Date(),
                )

                var sendResult = saveAttendanceList(list)
                if (sendResult)
                    isListFinish = true
            } catch (e: Exception){
                showErrorDialog = true
            } finally {
                _isLoading.value = false
            }*/

        }

    }

    private fun getCurrentUserRole(): UserRole {
        return runCatching {
            UserRole.valueOf(sharedPrf.getUserType())
        }.getOrElse {
            UserRole.USER // fallback seguro
        }
    }

    fun can(permission: Permission): Boolean {
        val role = getCurrentUserRole()
        return role.permissions().contains(permission)
    }

}