package com.iglesiabethesda.bethesdapp.members.ui.viewmodel

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.domain.usecase.UpdateMemberUseCase
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberUpdateViewModel @Inject constructor(
    private val update: UpdateMemberUseCase,
    @ApplicationContext private val context: Context
): ViewModel() {

    var memberName by mutableStateOf("")
    var memberApPa by mutableStateOf("")
    var memberApMa by mutableStateOf("")
    var memberHobby by mutableStateOf("")
    var memberJob by mutableStateOf("")
    var memberTel by mutableStateOf("")
    var memberAddress by mutableStateOf("")
    var memberEmergency by mutableStateOf("")
    var memberEmail by mutableStateOf("")
    var memberBirthDay by mutableStateOf("")
    val utilsFunctions: UtilsFunctions = UtilsFunctions()

    var memberNameError by mutableStateOf<String?>(null)
    var memberApPaError by mutableStateOf<String?>(null)
    var memberApMaError by mutableStateOf<String?>(null)
    var memberHobbyError by mutableStateOf<String?>(null)
    var memberTelError by mutableStateOf<String?>(null)
    var memberEmergencyError by mutableStateOf<String?>(null)
    var memberEmailError by mutableStateOf<String?>(null)
    var memberBirthDayError by mutableStateOf<String?>(null)

    var memberUid by mutableStateOf("")

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    var isMemberUpdate by mutableStateOf(false)
        private set

    var showErrorDialog by mutableStateOf(false)
        private set

    fun loadMember(member: MembersModel) {
        memberUid = member.uid
        memberName = member.name
        memberApPa = member.apPaterno
        memberApMa = member.apMaterno
        memberHobby = member.hobby
        memberJob = member.job
        memberTel = member.tel
        memberAddress = member.address
        memberEmergency = member.emergencyContact
        memberEmail = member.email
        memberBirthDay = utilsFunctions.formatDateTo_ddMMyyyy(member.birthDay)
    }


    fun updateMember() {
        val member = MembersModel(memberUid,memberName,memberApPa,memberApMa, memberHobby, memberJob,
            memberTel, memberAddress, memberEmergency, memberEmail,  utilsFunctions.parseDateFromStringBirthDay(memberBirthDay), 2,
            utilsFunctions.getCurrentDateTime(), utilsFunctions.getCurrentDateTime()
        )

        viewModelScope.launch {
            _isLoading.value = true
            val createdMember = update.invoke(member)
            createdMember.onSuccess {
                isMemberUpdate = true
            }.onFailure {
                isMemberUpdate = false
                showErrorDialog = true
            }
            _isLoading.value = false

        }
    }

    fun validateForm(): Boolean {
        var isValid = true

        // reset errores
        memberNameError = null
        memberApPaError = null
        memberApMaError = null
        memberHobbyError = null
        //memberJobError = null
        memberTelError = null
        //memberAddressError = null
        memberEmergencyError = null
        memberEmailError = null
        memberBirthDayError = null

        // Validaciones independientes para que marque todos los errores a la vez
        if (memberName.isBlank()) {
            memberNameError = context.getString(R.string.msg_error_member_name)
            isValid = false
        }
        if (memberApPa.isBlank()) {
            memberApPaError = context.getString(R.string.msg_error_member_ap_pa)
            isValid = false
        }
        if (memberApMa.isBlank()) {
            memberApMaError = context.getString(R.string.msg_error_member_ap_pa)
            isValid = false
        }
        if (memberHobby.isBlank()) {
            memberHobbyError = context.getString(R.string.msg_error_member_hobby)
            isValid = false
        }

        // Teléfono: validación básica (número mínimo de dígitos)
        if (memberTel.isBlank()) {
            memberTelError = context.getString(R.string.msg_error_member_tel)
            isValid = false
        } else if (memberTel.filter { it.isDigit() }.length < 10) {
            memberTelError = context.getString(R.string.msg_error_member_tel_invalid)
            isValid = false
        }

        if (memberEmergency.isBlank()) {
            memberEmergencyError = context.getString(R.string.msg_error_member_tel)
            isValid = false
        }else if (memberEmergency.filter { it.isDigit() }.length < 10) {
            memberEmergencyError = context.getString(R.string.msg_error_member_tel_invalid)
            isValid = false
        }
        // Email: validación básica
        if (memberEmail.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(memberEmail).matches()) {
            memberEmailError = context.getString(R.string.msg_error_member_email_invalid)
            isValid = false
        }
        // Fecha: no vacía y parseable (usa tu UtilsFunctions.parseDateFromStringBirthDay)
        if (memberBirthDay.isBlank()) {
            memberBirthDayError = context.getString(R.string.msg_error_member_birth_day)
            isValid = false
        }


        return isValid
    }

}