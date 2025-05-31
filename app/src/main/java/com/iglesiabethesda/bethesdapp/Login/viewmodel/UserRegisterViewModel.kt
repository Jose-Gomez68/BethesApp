package com.iglesiabethesda.bethesdapp.Login.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.Login.domain.CreateAccountUseCase
import com.iglesiabethesda.bethesdapp.Login.ui.model.UserSignIn
import com.iglesiabethesda.bethesdapp.members.data.MembersModel
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRegisterViewModel @Inject constructor(
    val createAccountUseCase: CreateAccountUseCase
): ViewModel() {

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

        val userSignIn = UserSignIn(memberName,memberName, memberEmail, "68120568",
            "68120568", 1, utilsFunctions.getCurrentDateTime(),
            utilsFunctions.getCurrentDateTime())
        viewModelScope.launch {
            val createdAccount = createAccountUseCase.invoke(userSignIn, member)
            if (createdAccount) {
                Log.e("EXITO SE CREO","CHINGON")
            } else {
                Log.e("ERROOORRRR","NOO LA POLITZIA")
            }
        }
    }

}