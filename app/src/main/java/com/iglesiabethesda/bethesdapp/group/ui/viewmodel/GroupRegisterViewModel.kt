package com.iglesiabethesda.bethesdapp.group.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModel
import com.iglesiabethesda.bethesdapp.group.domain.usecase.GroupRegisterUseCase
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.domain.usecase.GetAllMembersListExcepByUidUseCase
import com.iglesiabethesda.bethesdapp.util.SharedPreferencesConfig
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class GroupRegisterViewModel @Inject
constructor(
    private val getAllMembersListExcepByUidUseCase: GetAllMembersListExcepByUidUseCase,
    private val groupRegister: GroupRegisterUseCase,
    private val sharedPrf: SharedPreferencesConfig
): ViewModel() {

    private val _getMembers = mutableStateOf<Result<List<MembersModel>>?>(null)
    val getMembers: State<Result<List<MembersModel>>?> = _getMembers

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    var isGroupCreated by mutableStateOf(false)
        private set

    var showErrorDialog by mutableStateOf(false)
        private set

    var showErrorDialogInternet by mutableStateOf(false)
        private set

    /**HABRA 2 BOTONES UNO DE EDITAR
    * Y OTRO DE FINALIZAR O CERRAR GRUPO */
    var name by mutableStateOf("")
    var descrip by mutableStateOf("")
    var listMembers = mutableStateListOf<MembersModel>()// no puede ser null
    val utilsFunctions: UtilsFunctions = UtilsFunctions()

    var nameError by mutableStateOf<String?>(null)
    var descripError by mutableStateOf<String?>(null)
    var listMembersError by mutableStateOf<String?>(null)
    var listMembersError2 by mutableStateOf<String?>(null)


    fun groupRegister() {

        val group = GroupModel(
            "",
            name,
            descrip,
            listMembers.map { it.uid },
            1,
            utilsFunctions.getCurrentDateTime(),
            utilsFunctions.getCurrentDateTime(),
            null
        )

        viewModelScope.launch {
            _isLoading.value = true

            try {
                val result = withTimeout(4000) {   // 🔥 Fuerza timeout si se tarda
                    groupRegister.invoke(group)
                }

                result.onSuccess {
                    isGroupCreated = true
                }.onFailure {
                    showErrorDialog = true
                    isGroupCreated = false
                }

            } catch (e: Exception) {
                // 🔥 Aquí entra cuando NO HAY INTERNET
                showErrorDialogInternet = true
                isGroupCreated = false
            } finally {
                // 🔥 Esto ahora SIEMPRE se ejecuta
                _isLoading.value = false
            }
        }


    }

    fun getMember() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getAllMembersListExcepByUidUseCase.invoke(null)
            _getMembers.value = result

            _isLoading.value = false
        }
    }

    fun validateForm(): Boolean {
        Log.e("AQUI", "${listMembers}")
        var isValid = true

        // Reset de errores
        nameError = null
        descripError = null
        listMembersError = null
        listMembersError2 = null

        // Validaciones
        if (name.isBlank()) {
            nameError = "El nombre del grupo no puede estar vacío"
            isValid = false
        }

        if (descrip.isBlank()) {
            descripError = "La descripción no puede estar vacía"
            isValid = false
        }

        if (listMembers.size < 2) {
            listMembersError = "Debes agregar al menos 2 integrante"
            isValid = false
        }

        return isValid
    }


    fun resetShowErrorDialog() {
        showErrorDialog = false
    }

    fun resetShowErrorDialogInternet() {
        showErrorDialogInternet = false
    }

    fun resetIsLoading() {
        _isLoading.value = false
    }

}