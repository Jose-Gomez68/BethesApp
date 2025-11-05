package com.iglesiabethesda.bethesdapp.members.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.members.data.MembersModel
import com.iglesiabethesda.bethesdapp.members.domain.usecase.MemberRegisterUseCase
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberRegisterViewModel @Inject constructor(
    private val memberRegisterUseCase: MemberRegisterUseCase
) : ViewModel(){

    var memberName by mutableStateOf("")
    var memberApPa by mutableStateOf("")
    var memberApMa by mutableStateOf("")
    var memberHobby by mutableStateOf("")
    var memberJob by mutableStateOf("")
    var memberTel by mutableStateOf("")
    var memberEmergency by mutableStateOf("")
    var memberEmail by mutableStateOf("")
    var memberBirthDay by mutableStateOf("")
    val utilsFunctions: UtilsFunctions = UtilsFunctions()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    var isMemberCreated by mutableStateOf(false)
        private set

    var showErrorDialog by mutableStateOf(false)
        private set


    fun registerMember() {
        println("Nombre: $memberName")
        println("Apellido Paterno: $memberApPa")
        println("Apellido Materno: $memberApMa")
        println("Pasatiempo: $memberHobby")
        println("Oficio: $memberJob")
        println("Teléfono: $memberTel")
        println("Emergencia: $memberEmergency")
        println("Email: $memberEmail")
        println("Fecha Nacimiento: $memberBirthDay")

        val member = MembersModel("",memberName,memberApPa,memberApMa, memberHobby, memberJob,
            memberTel, memberEmergency, memberEmail,  utilsFunctions.parseDateFromStringBirthDay(memberBirthDay), 2,
            utilsFunctions.getCurrentDateTime(), utilsFunctions.getCurrentDateTime()
        )

        viewModelScope.launch {
            _isLoading.value = true
            val createdMember = memberRegisterUseCase.invoke(member)
            createdMember.onSuccess {
                Log.e("EXITO SE CREO","CHINGON")
                isMemberCreated = true
            }.onFailure {
                Log.e("ERROOORRRR","NOO LA POLITZIA")
                isMemberCreated = false
                showErrorDialog = true
            }
            _isLoading.value = false
            /*if (createdMember) {
                Log.e("EXITO SE CREO","CHINGON")
            } else {
                Log.e("ERROOORRRR","NOO LA POLITZIA")
            }*/
        }
    }

}